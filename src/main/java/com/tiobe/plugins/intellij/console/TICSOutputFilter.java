package com.tiobe.plugins.intellij.console;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.OpenFileHyperlinkInfo;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Filter} that can be used to extract relevant parts from a TICS Client log.
 */
@SuppressWarnings("unused")
public class TICSOutputFilter implements Filter {
    private static final Pattern VIOLATION_PATTERN = Pattern.compile("^(\\S.*)\\((\\d+)\\):$");
    private static final int VIOLATION_FILE_GROUP = 1;
    private static final int VIOLATION_LINE_NUMBER_GROUP = 2;
    @SuppressWarnings("Annotator")
    private static final Pattern SYNOPSIS_PATTERN = Pattern.compile("^\\s*(\\[\\*+NEW\\**\\]\\s*)?(.+)$");
    private static final Pattern RULE_PATTERN = Pattern.compile("^\\s*.+: .+ item (\\S+) \\(Category: (.+), Level: (\\d+)\\)$");
    private static final Pattern LINK_PATTERN = Pattern.compile("^\\s*(http://\\S+)$");
    private static final Pattern END_FILE_ANALYSIS_PATTERN = Pattern.compile("^end analysis:.*$");
    private static final Pattern END_PROJECT_ANALYSIS_PATTERN = Pattern.compile("^SUMMARY$");
    private static final Pattern START_ANALYSIS_PATTERN = Pattern.compile("^Analyzing\\s*.*$");
    @SuppressWarnings("Annotator")
    private static final Pattern MULTIPLE_FILES_PATTERN = Pattern.compile("^Analyzing\\s*\\d+\\/\\d+.*$");

    private final Project project;

    public TICSOutputFilter(final Project project) {
        this.project = project;
    }

    public Result applyFilter(final String line, final int entireLength) {
        return applyFilter(line, entireLength, TICSOutputFilter::createOpenFileHyperlink);
    }

    public Result applyFilter(final String line, final int entireLength, final HyperlinkFactory createOpenFileHyperlink) {
        final Matcher violationMatcher = VIOLATION_PATTERN.matcher(line.trim());
        if (violationMatcher.matches()) {
            final String filePath = violationMatcher.group(VIOLATION_FILE_GROUP);
            final int lineNumber = Integer.parseInt(violationMatcher.group(VIOLATION_LINE_NUMBER_GROUP)); // Calculate the offsets relative to the entire text.
            final int highlightStartOffset = entireLength - line.length() + violationMatcher.start();
            final int highlightEndOffset = entireLength - line.length() + violationMatcher.end() - 1;
            final HyperlinkInfo info = createOpenFileHyperlink.apply(project, filePath, lineNumber);
            if (info != null) {
                return new Result(highlightStartOffset, highlightEndOffset, info);
            }
        }
        return null;
    }

    @FunctionalInterface
    public interface HyperlinkFactory {
        HyperlinkInfo apply(Project project, String filePath, int lineNumber);
    }

    private static HyperlinkInfo createOpenFileHyperlink(final Project project, final String filePath, final int lineNumber) {
        final VirtualFile file = project.getBaseDir().getFileSystem().findFileByPath(filePath);
        if (file == null) {
            return null;
        }
        final OpenFileDescriptor fileDescriptor = new OpenFileDescriptor(project, file, lineNumber - 1, 0);
        return new OpenFileHyperlinkInfo(fileDescriptor);
    }
}
