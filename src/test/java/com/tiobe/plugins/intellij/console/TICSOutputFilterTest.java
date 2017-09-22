package com.tiobe.plugins.intellij.console;

import com.intellij.execution.filters.Filter.Result;
import com.intellij.execution.filters.Filter.ResultItem;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.openapi.project.Project;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TICSOutputFilterTest {
    final private String input;
    final private Result expected;
    private TICSOutputFilter instance;

    public TICSOutputFilterTest(String input, Result expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Iterable<?> data() {
        return Arrays.asList(new Object[][]{
                {"line\nnext line", null},
                {"/home/maikel/NetBeansProjects/PuzzleSolver/src/puzzlesolver/model/Position.java(3):", new Result(0, 82, new HyperlinkMock("/home/maikel/NetBeansProjects/PuzzleSolver/src/puzzlesolver/model/Position.java", 3))}
        });
    }

    @Before
    public void setUp() throws Exception {
        this.instance = new TICSOutputFilter(null);
    }

    @Test
    public void applyFilterTest() throws Exception {
        final String[] lines = input.split("\\r?\\n");
        final int entireLength = input.length();
        final List<ResultItem> results = Arrays.stream(lines)
                .map(line -> instance.applyFilter(line, entireLength,
                        (project, filePath, lineNumber) -> new HyperlinkMock(filePath, lineNumber)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        final Result actual = results.isEmpty() ? null : new Result(results);
        assertEqualResults(expected, actual);
    }

    static class HyperlinkMock implements HyperlinkInfo {
        final String filePath;
        final int lineNumber;

        HyperlinkMock(String filePath, int lineNumber) {
            this.filePath = filePath;
            this.lineNumber = lineNumber;
        }

        @Override
        public void navigate(Project project) {
            // do nothing
        }

        @Override
        public boolean includeInOccurenceNavigation() {
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            HyperlinkMock that = (HyperlinkMock) o;

            return lineNumber == that.lineNumber && filePath.equals(that.filePath);
        }

        @Override
        public int hashCode() {
            int result = filePath.hashCode();
            result = 31 * result + lineNumber;
            return result;
        }
    }

    private void assertEqualResults(Result expected, Result actual) {
        assertTrue(isEqualResults(expected, actual));
    }

    private boolean isEqualResults(Result expected, Result actual) {
        if (expected == null || actual == null) {
            return expected == actual;
        }
        if (!Objects.equals(expected.getFirstHyperlinkInfo(), actual.getFirstHyperlinkInfo())) {
            return false;
        }
        final List<ResultItem> expectedResultItems = expected.getResultItems();
        final List<ResultItem> actualResultItems = actual.getResultItems();
        if (expectedResultItems.size() != actualResultItems.size()) {
            return false;
        }
        for (int i = 0; i < expectedResultItems.size(); i++) {
            final ResultItem expectedItem = expectedResultItems.get(i);
            final ResultItem actualItem = actualResultItems.get(i);
            if (!isEqualResultItem(expectedItem, actualItem)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEqualResultItem(ResultItem expectedItem, ResultItem actualItem) {
        return expectedItem.getHighlightStartOffset() == actualItem.getHighlightStartOffset()
                && expectedItem.getHighlightEndOffset() == actualItem.getHighlightEndOffset();
    }
}