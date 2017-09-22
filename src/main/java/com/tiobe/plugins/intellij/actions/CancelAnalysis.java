package com.tiobe.plugins.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.tiobe.plugins.intellij.analyzer.TICSAnalyzer;

public class CancelAnalysis extends com.intellij.openapi.actionSystem.AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        TICSAnalyzer.getInstance().stop();
    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(TICSAnalyzer.getInstance().isRunning() && !TICSAnalyzer.getInstance().isStopping());
    }
}
