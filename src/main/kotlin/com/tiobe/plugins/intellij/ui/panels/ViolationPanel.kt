package com.tiobe.plugins.intellij.ui.panels

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.application.invokeLater
import com.intellij.ui.JBColor
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.scale.JBUIScale
import com.intellij.util.ui.UIUtil
import com.tiobe.plugins.intellij.utilities.Violation
import java.awt.BorderLayout
import java.awt.Cursor
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel

class ViolationPanel : JBPanel<ViolationPanel>(BorderLayout()) {

    private val panel = JPanel()
    private val header = JBLabel()
    private val subHeader = JBLabel()
    private val message = JBLabel()
    private val ruleHelp = JBLabel()
    private var link: String = ""

    init {
        add(ScrollPaneFactory.createScrollPane(panel), BorderLayout.CENTER)
        add(ruleHelp, BorderLayout.PAGE_END)

        panel.apply {
            layout = BoxLayout(panel, BoxLayout.Y_AXIS)
            add(header)
            add(Box.createVerticalStrut(5))
            add(subHeader)
            add(Box.createVerticalStrut(15))
            add(message)
        }

        header.apply {
            font = UIUtil.getLabelFont()
                .deriveFont(UIUtil.getLabelFont().size2D + JBUIScale.scale(5))
                .deriveFont(Font.BOLD)
        }

        subHeader.apply {
            foreground = JBColor.GRAY
        }

        ruleHelp.apply {
            foreground = JBColor.BLUE
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    if (e?.button == MouseEvent.BUTTON1) {
                        invokeLater { BrowserUtil.browse(link) }
                    }
                }
            })
        }
    }

    fun displayViolation(violation: Violation) {
        header.text = violation.synopsis
        subHeader.text = "Level: ${violation.level}   Language: ${violation.language}   Category: ${violation.category}"
        message.text = if (violation.message.isNullOrEmpty()) "" else violation.message

        ruleHelp.apply {
            violation.reference.let {
                if (it.isNullOrEmpty()) {
                    isVisible = false
                } else {
                    isVisible = true
                    text = if (violation.reference.isNullOrEmpty()) "" else "<html><u>Rule help</u></html>"
                    link = it
                }
            }
        }
    }
}