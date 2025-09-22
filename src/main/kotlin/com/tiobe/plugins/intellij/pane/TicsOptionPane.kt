package com.tiobe.plugins.intellij.pane

import com.intellij.ide.BrowserUtil
import com.intellij.ui.components.ActionLink
import com.intellij.ui.dsl.builder.panel
import javax.swing.Icon
import javax.swing.JOptionPane

class TicsOptionPane : JOptionPane() {
    companion object {
        private const val TITLE = "TICS: An error has occurred"

        fun showErrorMessageDialog(message: Any, icon: Icon? = null) {
            showMessageDialog(
                null,
                message,
                TITLE,
                ERROR_MESSAGE,
                icon
            )
        }

        fun showErrorMessageDialogWithLink(prefix: String, link: String, text: String = link, icon: Icon? = null) {
            val externalLink = ActionLink(text) {
                BrowserUtil.browse(link)
            }
            externalLink.setExternalLinkIcon()

            val message = panel {
                row(prefix) {
                    cell(externalLink).label(prefix)
                }
            }

            showMessageDialog(
                null,
                message,
                TITLE,
                ERROR_MESSAGE,
                icon
            )
        }
    }
}