package com.tiobe.plugins.intellij.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.tiobe.plugins.intellij.icons.TicsIcons

class AnalyzeFileOrFolder : AnalyzeFile() {
    override fun update(e: AnActionEvent) {
        PlatformDataKeys.VIRTUAL_FILE.getData(e.dataContext)?.let {
            if (it.isDirectory) {
                e.presentation.text = "Analyze Folder with TICS"
                e.presentation.icon = TicsIcons.ANALYZE_FOLDER
            } else {
                e.presentation.text = "Analyze File with TICS"
                e.presentation.icon = TicsIcons.ANALYZE_FILE
            }
        }

        super.update(e)
    }
}