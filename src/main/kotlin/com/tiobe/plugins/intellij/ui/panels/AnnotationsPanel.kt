package com.tiobe.plugins.intellij.ui.panels

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.tools.SimpleActionGroup
import com.intellij.ui.ScrollPaneFactory
import com.tiobe.plugins.intellij.ui.nodes.AnnotationNode
import com.tiobe.plugins.intellij.ui.tree.FileTree
import com.tiobe.plugins.intellij.ui.tree.FileTreeModelBuilder

class AnnotationsPanel(project: Project) : AbstractPanel(project) {
    private val fileTreeModelBuilder = FileTreeModelBuilder()
    private val tree = FileTree(project, fileTreeModelBuilder.getModel(), "No annotations to display")
    private var issue = 0

    init {
        val scrollPane = ScrollPaneFactory.createScrollPane(tree)
        super.setContent(scrollPane)
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
                    fileTreeModelBuilder.upsertFileWithAnnotations(
                        it, listOf(
                            AnnotationNode.Annotation(1 + issue),
                            AnnotationNode.Annotation(2 + issue),
                            AnnotationNode.Annotation(3 + issue)
                        )
                    )
                    issue += 3
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