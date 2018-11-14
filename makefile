# $Id$
# This file is part of the overall build.
.SILENT:
.SUFFIXES:

.PHONY: all clean build runide test coveragereport rebuild package relnotes clean_relnotes publish

DAEMON := --no-daemon # to prevent using the Gradle Daemon in CI
GRADLE := ./gradlew -PSVNVERSION="$(SVNVERSION)" $(DAEMON)
SVNVERSION := $(shell svn info . | sed -n "s/Last Changed Rev: //p")
TOOL := TICSIntelliJ

all: build coveragereport

build:
	$(GRADLE) buildplugin

clean: clean_relnotes
	$(GRADLE) clean

runide:
	$(GRADLE) runide

test:
	$(GRADLE) test

coveragereport:
	$(GRADLE) test jacocoTestReport
ifneq ($(TESTCOVERAGE_RESULTDIR),)
	$(MKDIR) "$(TESTCOVERAGE_RESULTDIR)/ideintellij/"
	cp $(COMPONENTSDIR)/ideintellij/build/reports/jacoco/test/jacocoTestReport.xml "$(TESTCOVERAGE_RESULTDIR)/ideintellij/"
endif

rebuild: clean all

package: build

# The SVN repository number from which revisions onwards one must
# collect release notes.
STARTREV := 33765
relnotes:
ifeq ($(OS),Windows_NT)
	svn log --xml -r $(SVNVERSION):$(STARTREV) | msxsl -o $(TOOL)-relnotes.html - svn-log.xslt
else
	svn log --xml -r $(SVNVERSION):$(STARTREV) | xsltproc -o $(TOOL)-relnotes.html svn-log.xslt -
endif

clean_relnotes:
	rm -f $(TOOL)-relnotes.html

TICSVERSION=$(shell cat ../../make/TICSVERSION)
DEST=imposter:/var/home/wilde/ticsweb/pub/plugins/intellij

publish: package
	scp build/distributions/TICSIntelliJ.zip $(DEST)/$(TOOL)-$(TICSVERSION).$(SVNVERSION).zip
	scp $(TOOL)-relnotes.html $(DEST)
