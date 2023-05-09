package com.tiobe.plugins.intellij.ui

import com.intellij.openapi.project.Project
import javax.swing.JLabel

class AnnotationsPanel(project: Project) : AbstractPanel(project) {
    init {
        addLabel()
    }

    private fun addLabel() {
        super.setContent(JLabel("Annotations"))
    }
}