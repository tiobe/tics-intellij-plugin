package com.tiobe.plugins.intellij.pane

import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.panel
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialogWithLink
import com.tiobe.plugins.intellij.utilities.TicsAuthToken
import org.apache.hc.core5.net.URIBuilder
import javax.swing.JTextField

class ViewerUrlDialogWrapper(value: String? = null) : DialogWrapper(true) {
    private var jTextField = JTextField(value)
    var input: String? = null

    init {
        title = "TICS Viewer Configuration Url"
        init()
    }

    override fun createCenterPanel(): DialogPanel {
        return panel {
            row {
                label("Insert the TICS Viewer configuration url.")
            }
            row {
                cell(jTextField)
            }
            row {
                comment("http(s)://domain(:port)/tiobeweb/section/api/cfg?name=configuration")
            }
        }
    }

    override fun doValidate(): ValidationInfo? {
        val uri = URIBuilder(jTextField.text)

        if (uri.scheme != "http" && uri.scheme != "https") {
            return ValidationInfo("Missing the protocol: http(s)://", jTextField)
        } else if (!uri.path.endsWith("/api/cfg")) {
            return ValidationInfo("Missing the path /api/cfg", jTextField)
        } else if (!uri.queryParams.any {
                it.name == "name" && it.value != ""
            }) {
            return ValidationInfo("Missing the configuration. Eg: /cfg?name=default", jTextField)
        }
        return null
    }

    override fun doOKAction() {
        val validAuthTokenInfo = TicsAuthToken.validateAndGet(jTextField.text)
        when (validAuthTokenInfo.code) {
            200 -> {
                input = jTextField.text
                close(OK_EXIT_CODE)
            }

            401 -> {
                val baseUrl = jTextField.text.split("/api/")[0]
                val url = "$baseUrl/UserSettings.html#page=authToken"
                showErrorMessageDialogWithLink(ErrorMessages.NO_AUTH_FILE, url)
            }

            408 -> {
                val baseUrl = jTextField.text.split("/api/")[0]
                showErrorMessageDialogWithLink(validAuthTokenInfo.message, baseUrl)
            }

            else -> {
                showErrorMessageDialog(validAuthTokenInfo.message)
            }
        }
    }
}