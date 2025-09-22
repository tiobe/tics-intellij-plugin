package com.tiobe.plugins.intellij.ui.panels

import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.ScrollPaneFactory
import com.tiobe.plugins.intellij.ui.analysis.ProcessAnalysis
import com.tiobe.plugins.intellij.ui.tree.FileTree
import com.tiobe.plugins.intellij.ui.tree.FileTreeModelBuilder
import org.jetbrains.annotations.NonNls

class ViolationsPanel(project: Project) : AbstractPanel(project) {
    private val fileTreeModelBuilder = FileTreeModelBuilder()
    private val informationPanel = InformationPanel()
    private val tree = FileTree(project, fileTreeModelBuilder.getModel(), informationPanel, "No annotations to display")

    init {
        val splitter = OnePixelSplitter(0.5f)
        splitter.firstComponent = ScrollPaneFactory.createScrollPane(tree)
        splitter.secondComponent = informationPanel

        super.setContent(splitter)

        ProcessAnalysis(project, fileTreeModelBuilder)
    }

    override fun getData(dataId: @NonNls String): Any? {
        return tree.getData(dataId)
    }
}