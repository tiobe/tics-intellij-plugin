package com.tiobe.plugins.intellij.utilities

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction

object TicsActions {
    val analyzeFile: AnAction = ActionManager.getInstance().getAction("TICS.AnalyzeFile")
    val analyzeProject: AnAction = ActionManager.getInstance().getAction("TICS.AnalyzeProject")
    val cancelAnalysis: AnAction = ActionManager.getInstance().getAction("TICS.CancelAnalysis")
    val clearConsole: AnAction = ActionManager.getInstance().getAction("TICS.ClearConsole")
    val installTics: AnAction = ActionManager.getInstance().getAction("TICS.InstallTics")
    val runTicsConfig: AnAction = ActionManager.getInstance().getAction("TICS.RunTicsConfig")
}