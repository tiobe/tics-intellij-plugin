# $Id$
# This file is part of the overall build.
.SILENT:
.SUFFIXES:

.PHONY: all check clean build runide test coveragereport rebuild package relnotes clean_relnotes publish

SVNVERSION := $(shell svn info . | sed -n "s/Last Changed Rev: //p")
DAEMON := --no-daemon # to prevent using the Gradle Daemon in CI
GRADLE = $(CURDIR)/gradlew -PSVNVERSION="$(SVNVERSION)" $(DAEMON)
TOOL := TICSIntelliJ

all: build coveragereport

build:
	$(GRADLE) buildplugin
	cp build/distributions/$(TOOL)-$(TICSVERSION).$(SVNVERSION).zip build/distributions/TICSIntelliJ.zip

check:
	$(GRADLE) check -x test

clean: clean_relnotes
	$(GRADLE) clean

runide:
	$(GRADLE) runide

test: check
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
DEST=absolem:/home/wilde/ticsweb/pub/plugins/intellij

publish: package relnotes
	scp build/distributions/$(TOOL)-$(TICSVERSION).$(SVNVERSION).zip $(DEST)
	scp $(TOOL)-relnotes.html $(DEST)
