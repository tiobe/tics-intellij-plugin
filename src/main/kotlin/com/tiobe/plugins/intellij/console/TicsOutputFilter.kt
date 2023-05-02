package com.tiobe.plugins.intellij.console

import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.HyperlinkInfo
import com.intellij.execution.filters.OpenFileHyperlinkInfo
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * [Filter] that can be used to extract relevant parts from a TICS Client log.
 */
class TicsOutputFilter(private val project: Project?) : Filter {

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        val violationMatcher: Matcher = VIOLATION_PATTERN.matcher(line.trim { it <= ' ' })
        if (violationMatcher.matches()) {
            val filePath: String = violationMatcher.group(VIOLATION_FILE_GROUP)
            // Calculate the offsets relative to the entire text.
            val lineNumber: Int = violationMatcher.group(VIOLATION_LINE_NUMBER_GROUP).toInt()
            val highlightStartOffset = entireLength - line.length + violationMatcher.start()
            val highlightEndOffset = entireLength - line.length + violationMatcher.end() - 1
            val info: HyperlinkInfo? = project?.let { createOpenFileHyperlink(it, filePath, lineNumber) }
            return Filter.Result(highlightStartOffset, highlightEndOffset, info)
        }
        return null
    }

    private fun createOpenFileHyperlink(project: Project, filePath: String, lineNumber: Int): HyperlinkInfo? {
        val file = project.projectFile?.fileSystem?.findFileByPath(filePath) ?: return null
        val fileDescriptor = OpenFileDescriptor(project, file, lineNumber - 1, 0)
        return OpenFileHyperlinkInfo(fileDescriptor)
    }

    companion object {
        private val VIOLATION_PATTERN = Pattern.compile("^(\\S.*)\\((\\d+)\\):$")
        private const val VIOLATION_FILE_GROUP = 1
        private const val VIOLATION_LINE_NUMBER_GROUP = 2
        private val SYNOPSIS_PATTERN = Pattern.compile("^\\s*(\\[\\*+NEW\\**\\]\\s*)?(.+)$")
        private val RULE_PATTERN = Pattern.compile("^\\s*.+: .+ item (\\S+) \\(Category: (.+), Level: (\\d+)\\)$")
        private val LINK_PATTERN = Pattern.compile("^\\s*(http://\\S+)$")
        private val END_FILE_ANALYSIS_PATTERN = Pattern.compile("^end analysis:.*$")
        private val END_PROJECT_ANALYSIS_PATTERN = Pattern.compile("^SUMMARY$")
        private val START_ANALYSIS_PATTERN = Pattern.compile("^Analyzing\\s*.*$")
        private val MULTIPLE_FILES_PATTERN = Pattern.compile("^Analyzing\\s*\\d+\\/\\d+.*$")
    }
}
