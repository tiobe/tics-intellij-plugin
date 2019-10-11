package com.tiobe.plugins.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.tiobe.plugins.intellij.analyzer.TICSAnalyzer;

/**
 * {@link com.intellij.openapi.actionSystem.AnAction} that cancels the currently running TICS analysis.
 */
public class CancelAnalysis extends com.intellij.openapi.actionSystem.AnAction {
    @Override
    public void actionPerformed(final AnActionEvent anActionEvent) {
        TICSAnalyzer.getInstance().stop();
    }

    @Override
    public void update(final AnActionEvent event) {
        event.getPresentation().setEnabled(TICSAnalyzer.getInstance().isRunning() && !TICSAnalyzer.getInstance().isStopping());
    }
}
