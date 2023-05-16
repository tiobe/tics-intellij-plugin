package com.tiobe.plugins.intellij.ui.panels

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.tools.SimpleActionGroup
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.ScrollPaneFactory
import com.tiobe.plugins.intellij.analysis.AnalysisListener
import com.tiobe.plugins.intellij.ui.analysis.ProcessAnalysis
import com.tiobe.plugins.intellij.ui.tree.FileTree
import com.tiobe.plugins.intellij.ui.tree.FileTreeModelBuilder

class ViolationsPanel(project: Project) : AbstractPanel(project) {
    private val fileTreeModelBuilder = FileTreeModelBuilder()
    private val tree = FileTree(project, fileTreeModelBuilder.getModel(), "No annotations to display")

    init {
        val splitter = OnePixelSplitter(0.5f)
        splitter.firstComponent = ScrollPaneFactory.createScrollPane(tree)
        splitter.secondComponent = InformationPanel

        super.setContent(splitter)

        ProcessAnalysis(project, fileTreeModelBuilder)
    }

    override fun createActionGroup(): SimpleActionGroup {
        val actionGroup = super.createActionGroup()
        actionGroup.add(addNode())
        actionGroup.add(removeNode())
        return actionGroup
    }

    private fun addNode(): AnAction {
        return object : AnAction() {
            override fun actionPerformed(e: AnActionEvent) {
                FileEditorManager.getInstance(project).selectedEditor?.file?.let {
                    project.messageBus.syncPublisher(AnalysisListener.TOPIC).change(listOf(it.path))
                }
            }
        }
    }

    private fun removeNode(): AnAction {
        return object : AnAction() {
            override fun actionPerformed(e: AnActionEvent) {
                FileEditorManager.getInstance(project).selectedEditor?.file?.let {
                    fileTreeModelBuilder.removeFile(it)
                }
            }
        }
    }
}