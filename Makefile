# Variables
SHELL := /bin/bash

REPO  := docker.impello.co.uk
NAME  := $(shell python .tools/pom.py artifactId)
TAG   ?= $(shell python .tools/pom.py version)
IMAGE := ${REPO}/${NAME}:${TAG}
GROUPID := $(subst .,/,$(shell .tools/pom.py groupId))

ARTIFACTORY := http://build-repo-dc2.prod.impello.co.uk:8081/artifactory
CHANNEL     ?= libs-release-local

JAVA_SOURCE_FILES := $(shell find ./src -name '*.java')

USER_ID ?= $(shell stat -c "%u:%g" .)

BRANCH_NAME ?= $(shell git rev-parse --abbrev-ref HEAD 2>/dev/null)
DOCKER_PROJECT_MODIFICATOR := $(shell echo ${BRANCH_NAME} | sed 's@origin/@@' | sed 's@/@_@')
DOCKER_COMPOSE_OPTS ?= --project-name ${NAME}${DOCKER_PROJECT_MODIFICATOR}

## Applications
DOCKER_COMPOSE ?= IMAGE=${IMAGE} USER_ID=${USER_ID} docker-compose ${DOCKER_COMPOSE_OPTS} -f ./docker/docker-compose.build.yml

DOCKER ?= docker
MVN ?= ${DOCKER_COMPOSE} run --rm mvn -B

# Helpers
all: depend build

debug:
	${DOCKER_COMPOSE} -f ./docker/docker-compose.debug.yml -f ./docker/docker-compose.yml -f ./docker/docker-compose.services.yml up --force-recreate app

run:
	${DOCKER_COMPOSE} -f ./docker/docker-compose.yml -f ./docker/docker-compose.services.yml up --force-recreate app

tinker:
	${DOCKER_COMPOSE} -f ./docker/docker-compose.build.yml run --rm jdk sh

.PHONY: all run tinker

# Dependencies
depend:
	${MVN} dependency:resolve

.PHONY: depend

# Building...

#... library
target/${NAME}-${TAG}.jar: pom.xml ${JAVA_SOURCE_FILES}
	${MVN} package org.apache.maven.plugins:maven-jar-plugin:test-jar source:jar-no-fork source:test-jar-no-fork -Dskip.unit.tests=true

build: target/${NAME}-${TAG}.jar

publish:
	curl --netrc -X PUT \
		--upload-file target/${NAME}-${TAG}.jar \
		"${ARTIFACTORY}/${CHANNEL}/${GROUPID}/${NAME}/${TAG}/${NAME}-${TAG}.jar"
	curl --netrc -X PUT \
		--upload-file target/${NAME}-${TAG}-sources.jar \
		"${ARTIFACTORY}/${CHANNEL}/${GROUPID}/${NAME}/${TAG}/${NAME}-${TAG}-sources.jar"
	curl --netrc -X PUT \
		--upload-file target/${NAME}-${TAG}-tests.jar \
		"${ARTIFACTORY}/${CHANNEL}/${GROUPID}/${NAME}/${TAG}/${NAME}-${TAG}-tests.jar"
	curl --netrc -X PUT \
		--upload-file target/${NAME}-${TAG}-test-sources.jar \
		"${ARTIFACTORY}/${CHANNEL}/${GROUPID}/${NAME}/${TAG}/${NAME}-${TAG}-test-sources.jar"
	curl --netrc -X PUT \
		--upload-file pom.xml \
		"${ARTIFACTORY}/${CHANNEL}/${GROUPID}/${NAME}/${TAG}/${NAME}-${TAG}.pom"

#... image
target/app.jar: build
	yes | cp -i target/${NAME}-${TAG}.jar target/app.jar

image: target/app.jar
	${DOCKER} build -f ./docker/Dockerfile -t ${IMAGE} .

.PHONY: build image publish

# Testing
test: unit-tests integration-tests

unit-tests:
	${MVN} test

integration-tests:
	${MVN} verify -Dskip.unit.tests=true

functional-tests: DOCKER_COMPOSE := ${DOCKER_COMPOSE} -f ./docker/docker-compose.test.yml -f ./docker/docker-compose.services.yml
functional-tests: image
	${DOCKER_COMPOSE} config
	${DOCKER_COMPOSE} run --rm functional-tests; \
		RESULT=$$?; \
		echo '----------------------------------------'; \
		echo 'APP LOGS:'; \
		echo '----------------------------------------'; \
		${DOCKER_COMPOSE} logs app; \
		echo '----------------------------------------'; \
		echo 'WIREMOCK LOGS:'; \
		echo '----------------------------------------'; \
		${DOCKER_COMPOSE} logs wiremock; \
		echo '----------------------------------------'; \
		echo 'DOWN:'; \
		echo '----------------------------------------'; \
		${DOCKER_COMPOSE} down --remove-orphans; \
		echo '----------------------------------------'; \
		echo 'CLEAN:'; \
		echo '----------------------------------------'; \
		make clean-docker; \
		exit $$RESULT

.PHONY: test unit-tests integration-tests functional-tests

# QA
qa: validate checkstyle pmd

validate:
	${MVN} validate

checkstyle:
	${MVN} checkstyle:check

pmd:
	${MVN} pmd:check

.PHONY: qa validate checkstyle

# Cleaning
clean:
	${MVN} clean

clean-docker: DOCKER_COMPOSE_FILES := $(sort $(wildcard ./docker/docker-compose*.yml))
clean-docker: DOCKER_COMPOSE_FILES := $(patsubst %.yml,-f %.yml, ${DOCKER_COMPOSE_FILES})
clean-docker:
	${DOCKER_COMPOSE} ${DOCKER_COMPOSE_FILES} stop
	${DOCKER_COMPOSE} ${DOCKER_COMPOSE_FILES} rm --force -v

clean-mvn:
	rm -rf .tools/mvn/repository/*

clean-all: clean clean-docker clean-mvn

.PHONY: clean clean-docker clean-mvn clean-all
