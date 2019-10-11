package com.tiobe.plugins.intellij.analyzer;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessListener;
import com.intellij.openapi.util.Key;

public final class TICSAnalyzer {
    private static final TICSAnalyzer INSTANCE = new TICSAnalyzer();

    private ProcessHandler handler;
    private TICSProcessState state = TICSProcessState.STOPPED;

    public enum TICSProcessState {
        STOPPED, STOPPING, RUNNING
    }

    private TICSAnalyzer() {
    }

    public ProcessHandler run(final GeneralCommandLine command) throws ExecutionException {
        return run(command, false);
    }

    public ProcessHandler run(final GeneralCommandLine command, final boolean ignoreState)
            throws ExecutionException {
        final ProcessHandler handler = new OSProcessHandler(command);
        final ProcessListener listener = new TICSProcessListener();
        if (!ignoreState) {
            handler.addProcessListener(listener);
            handler.startNotify();
            this.handler = handler;
        }
        return handler;
    }

    @SuppressWarnings("NullableProblems")
    private class TICSProcessListener implements ProcessListener {
        @Override
        public void startNotified(final ProcessEvent processEvent) {
            state = TICSProcessState.RUNNING;
        }

        @Override
        public void processTerminated(final ProcessEvent processEvent) {
            state = TICSProcessState.STOPPED;
        }

        @Override
        public void processWillTerminate(final ProcessEvent processEvent, final boolean willBeDestroyed) {
            if (willBeDestroyed) {
                state = TICSProcessState.STOPPING;
            }
        }

        @Override
        @SuppressWarnings("rawtypes")
        public void onTextAvailable(final ProcessEvent processEvent, final Key key) {
        }
    }

    public boolean isRunning() {
        return state == TICSProcessState.RUNNING || state == TICSProcessState.STOPPING;
    }

    public boolean isStopping() {
        return state == TICSProcessState.STOPPING;
    }

    public static TICSAnalyzer getInstance() {
        return INSTANCE;
    }

    public void stop() {
        this.handler.destroyProcess();
    }
}
