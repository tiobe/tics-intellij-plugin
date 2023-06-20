package com.tiobe.plugins.intellij.ui.panels

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.util.ui.JBUI
import com.tiobe.plugins.intellij.utilities.Violation
import java.awt.BorderLayout

class InformationPanel : JBPanel<InformationPanel>(BorderLayout()) {
    private val emptyText = JBLabel("Select a violation to display")
    private val violationPanel = ViolationPanel()

    init {
        border = JBUI.Borders.empty(10)

        add(emptyText, BorderLayout.NORTH)
        add(violationPanel, BorderLayout.CENTER)

        noViolationToDisplay()
    }

    fun noViolationToDisplay() {
        emptyText.isVisible = true
        violationPanel.isVisible = false
    }

    fun displayViolation(violation: Violation) {
        emptyText.isVisible = false
        violationPanel.isVisible = true

        violationPanel.displayViolation(violation)
    }
}