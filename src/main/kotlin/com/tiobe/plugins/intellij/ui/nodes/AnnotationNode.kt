package com.tiobe.plugins.intellij.ui.nodes

import com.tiobe.plugins.intellij.ui.tree.TreeCellRenderer

class AnnotationNode(private val annotation: Annotation) : AbstractNode() {

    init {
        allowsChildren = false
    }

    class Annotation(int: Int) {
        val file: String = "file $int"
    }


    override fun render(renderer: TreeCellRenderer) {
        renderer.append(annotation.file)
    }
}