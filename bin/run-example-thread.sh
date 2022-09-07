#!/usr/bin/env bash

EXAMPLE_PROJECT='dragonfly-example-thread'
EXAMPLE_JAR='dragonfly-example-thread-0.1.jar'
EXAMPLE_CLASS_NAME='example.thread.ThreadMain'
EXAMPLE_JVM_PARAM=''

nohup sh ./run-example.sh ${EXAMPLE_PROJECT} ${EXAMPLE_JAR} ${EXAMPLE_CLASS_NAME} ${EXAMPLE_JVM_PARAM} >nohup.log 2>&1 &
tail -f nohup.log
