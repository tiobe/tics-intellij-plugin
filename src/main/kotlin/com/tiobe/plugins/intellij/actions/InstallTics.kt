package com.tiobe.plugins.intellij.actions

import com.google.gson.Gson
import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.showOkCancelDialog
import com.intellij.openapi.util.SystemInfo
import com.tiobe.model.AlertMessages
import com.tiobe.plugins.intellij.analysis.ProcessRunner
import com.tiobe.plugins.intellij.console.TicsConsole
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane
import com.tiobe.plugins.intellij.pane.ViewerUrlDialogWrapper
import com.tiobe.plugins.intellij.utilities.TicsAuthToken
import com.tiobe.utility.TicsHttpClient
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.net.URIBuilder
import java.net.URL


/**
 * Installs TICS on the host machine.
 */
class InstallTics : AnAction() {
    private var project: Project? = null
    override fun actionPerformed(e: AnActionEvent) {
        project = PlatformDataKeys.PROJECT.getData(e.dataContext)

        var tics = System.getenv("TICS")
        try {
            if (tics == null) {
                val dialog = ViewerUrlDialogWrapper()
                if (dialog.showAndGet()) {
                    tics = dialog.input
                }
            }
            if (tics != null) {
                installTics(getInstallCommand(tics))
            }
        } catch (e: Exception) {
            if (e.message == ErrorMessages.NO_AUTH_FILE) {
                val authUrl = "${getBaseUrl(tics)}}/UserSettings.html#page=authToken"
                TicsOptionPane.showErrorMessageDialogWithLink(ErrorMessages.NO_AUTH_FILE, authUrl)
            } else {
                TicsOptionPane.showErrorMessageDialog(e)
            }
        }

    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = TicsConsole.isInitialized()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    private fun installTics(command: GeneralCommandLine) {
        if (project == null) {
            TicsOptionPane.showErrorMessageDialog(
                ErrorMessages.NO_ACTIVE_PROJECT
            )
            return
        }

        val handler: OSProcessHandler
        try {
            handler = ProcessRunner.run(project!!, command, callback = ::onHandlerTerminated)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            TicsOptionPane.showErrorMessageDialog(
                ErrorMessages.TICS_INSTALL_FAILED
            )
            return
        }
        TicsConsole.attachToProcess(handler)
    }

    /**
     *
     */
    private fun onHandlerTerminated(exitCode: Int) {
        if (exitCode == 0) {
            val ok = showOkCancelDialog(
                "Reload Required",
                "In order to complete the TICS installation, the IDE needs to be closed and restarted. Do you want to do this now?",
                "Shutdown Now",
                "Later",
                AllIcons.General.QuestionDialog
            )
            if (ok == 0) {
                // Close IntelliJ
                ApplicationManager.getApplication().exit()
            }
        }
    }


    /**
     * Retrieves the installation command from the configured TICS Viewer.
     * Uses environment variables:
     * - TICSTRUSTSTRATEGY
     * - TICS
     * @param url TICS Viewer configuration url.
     */
    private fun getInstallCommand(url: String): GeneralCommandLine {
        try {
            if (SystemInfo.isLinux) {
                val installUrl = getInstallUrl("linux", url, TicsAuthToken.getAuthToken())
                return GeneralCommandLine(listOf("bash", "-c", getLinuxInstall(installUrl))).withEnvironment("TICSIDE", "INTELLIJ")
            } else if (SystemInfo.isWindows) {
                val installUrl = getInstallUrl("windows", url, TicsAuthToken.getAuthToken())
                return GeneralCommandLine(listOf("powershell", getWindowsInstall(installUrl)))
            }
            throw Exception("No install command found for platform: ${SystemInfo.OS_NAME}.")
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Returns the TICS web base url.
     * @param platform Platform on which the IDE is running.
     * @param url TICS Viewer configuration url.
     * @param authToken (optional) TICS authentication token.
     */
    private fun getInstallUrl(platform: String, url: String, authToken: String?): String {
        val baseUrl = getBaseUrl(url).toString()
        val installTicsUrl = URIBuilder(url).addParameter("platform", platform).addParameter("url", baseUrl)

        val client = TicsHttpClient()
        client.setRequestedWithTics()
        authToken?.let {
            client.setBasicAuthorizationHeader(it)
        }

        val httpGet = HttpGet(installTicsUrl.toString())
        client.createHttpClient().use { httpClient ->
            val response = httpClient.execute(httpGet) {
                val entity = EntityUtils.toString(it.entity)
                when (it.code) {
                    200 -> {
                        entity
                    }

                    401 -> {
                        throw Exception(ErrorMessages.NO_AUTH_FILE)
                    }

                    else -> {
                        val alertMessages = Gson().fromJson(entity, AlertMessages::class.java)
                        throw Exception(alertMessages.alertMessages[0].header)
                    }
                }
            }
            val artifacts = Gson().fromJson(response, ArtifactsResponse::class.java)
            return baseUrl + artifacts.links.installTics
        }
    }

    private fun getBaseUrl(url: String): URL {
        val apiMarker = "/api/"

        if (url.contains(apiMarker)) {
            return URL(url.split(apiMarker)[0])
        }
        throw Exception("Incorrect TICS Viewer url was given.")
    }

    /**
     * Returns the installation command for Linux.
     * @param installUrl TICS installation url.
     */
    private fun getLinuxInstall(installUrl: String): String {
        var trustStrategy = ""
        if (System.getenv("TICSTRUSTSTRATEGY") == "all" || System.getenv("TICSTRUSTSTRATEGY") == "self-signed") {
            trustStrategy = "--insecure"
        }
        return "source <(curl --silent $trustStrategy '$installUrl')"
    }

    /**
     * Returns the installation command for Windows.
     * @param installUrl TICS installation url.
     */
    private fun getWindowsInstall(installUrl: String): String {
        var trustStrategy = ""
        if (System.getenv("TICSTRUSTSTRATEGY") == "all" || System.getenv("TICSTRUSTSTRATEGY") == "self-signed") {
            trustStrategy = "[System.Net.ServicePointManager]::ServerCertificateValidationCallback = {\$true};"
        }
        return "Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; \$env:TICSIDE='INTELLIJ'; $trustStrategy iex ((New-Object System.Net.WebClient).DownloadString('$installUrl')); \$env:TICSIDE=\$null;"
    }

    private data class ArtifactsResponse(
        val links: Links
    )

    private data class Links(
        val setPropPath: String,
        val queryArtifact: String,
        val uploadArtifact: String,
        val installTics: String?,
    )
}