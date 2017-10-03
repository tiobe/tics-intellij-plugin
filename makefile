# $Id$
# This file is part of the overall build.
.SILENT:
.SUFFIXES:

.PHONY: all clean build runide test coveragereport rebuild 

DAEMON := --no-daemon # to prevent using the Gradle Daemon in CI
GRADLE := ./gradlew -PSVNVERSION="$(SVNVERSION)" $(DAEMON)

all: build

build:
	$(GRADLE) buildplugin

clean:
	$(GRADLE) clean

runide:
	$(GRADLE) runide

test:
	$(GRADLE) test

coveragereport: test
	$(GRADLE) jacocoTestReport

rebuild: clean all
