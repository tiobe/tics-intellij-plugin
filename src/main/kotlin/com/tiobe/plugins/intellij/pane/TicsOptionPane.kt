package com.tiobe.plugins.intellij.pane

import com.intellij.ide.BrowserUtil
import com.intellij.ui.components.ActionLink
import com.intellij.util.ui.UI
import javax.swing.Icon
import javax.swing.JOptionPane

class TicsOptionPane : JOptionPane() {
    companion object {
        private const val title = "TICS: An error has occurred"

        fun showErrorMessageDialog(message: Any, icon: Icon? = null) {
            showMessageDialog(
                null,
                message,
                title,
                ERROR_MESSAGE,
                icon
            )
        }

        fun showErrorMessageDialogWithLink(prefix: String, link: String, text: String = link, icon: Icon? = null) {
            val externalLink = ActionLink(text) {
                BrowserUtil.browse(link)
            }
            externalLink.setExternalLinkIcon()
            showMessageDialog(
                null,
                UI.PanelFactory.panel(externalLink).withLabel(prefix).moveLabelOnTop().createPanel(),
                title,
                ERROR_MESSAGE,
                icon
            )
        }
    }
}