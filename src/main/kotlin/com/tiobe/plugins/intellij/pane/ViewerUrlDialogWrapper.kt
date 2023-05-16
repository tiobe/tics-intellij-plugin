package com.tiobe.plugins.intellij.pane

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.util.ui.UI
import com.tiobe.plugins.intellij.errors.ErrorMessages
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialog
import com.tiobe.plugins.intellij.pane.TicsOptionPane.Companion.showErrorMessageDialogWithLink
import com.tiobe.plugins.intellij.utilities.TicsAuthToken
import org.apache.hc.core5.net.URIBuilder
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JTextField

class ViewerUrlDialogWrapper : DialogWrapper(true) {
    private val jTextField: JTextField = JTextField()
    var input: String = ""

    init {
        title = "TICS Viewer Configuration Url"
        init()
    }

    override fun createCenterPanel(): JComponent {
        return UI.PanelFactory.grid()
            .add(UI.PanelFactory.panel(JLabel("Insert the TICS Viewer configuration url.")))
            .add(
                UI.PanelFactory.panel(jTextField)
                    .withComment("http(s)://domain(:port)/tiobeweb/section/api/cfg?name=configuration")
            )
            .createPanel()
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

            else -> {
                showErrorMessageDialog(validAuthTokenInfo.message)
            }
        }
    }
}