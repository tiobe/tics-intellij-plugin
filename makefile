# $Id$
# This file is part of the overall build.
.SILENT:
.SUFFIXES:

.PHONY: all clean build runide test coveragereport rebuild 

DAEMON := --no-daemon # to prevent using the Gradle Daemon in CI
GRADLE := ./gradlew -PSVNVERSION="$(SVNVERSION)" $(DAEMON)

all: coveragereport

build:
	$(GRADLE) buildplugin

clean:
	$(GRADLE) clean

runide:
	$(GRADLE) runide

test:
	$(GRADLE) test

coveragereport:
	$(GRADLE) test jacocoTestReport
ifneq ($(TESTCOVERAGE_RESULTDIR),)
	-mkdir -p "$(TESTCOVERAGE_RESULTDIR)/ideintellij/"
	cp build/reports/jacoco/test/jacocoTestReport.xml "$(TESTCOVERAGE_RESULTDIR)/ideintellij/"
endif

rebuild: clean all
