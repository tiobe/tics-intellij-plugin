package com.tiobe.plugins.intellij.analyzer

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement


class TicsProblemReporter : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val document = PsiDocumentManager.getInstance(element.project).getDocument(element.containingFile)
        document?.let {
            val lineNumber = 36
            val column = 10
            val lineStartOffset = it.getLineStartOffset(lineNumber - 1)
            val offSet = lineStartOffset + column
            val range = TextRange(offSet - 1, offSet)
            holder.newAnnotation(HighlightSeverity.WARNING, "Message").range(range)
                .highlightType(ProblemHighlightType.GENERIC_ERROR_OR_WARNING).create()
        }
    }
}