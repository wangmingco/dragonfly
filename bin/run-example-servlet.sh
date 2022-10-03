#!/usr/bin/env bash

EXAMPLE_PROJECT='dragonfly-example-servlet'
EXAMPLE_JAR='dragonfly-example-servlet-0.1.jar'
EXAMPLE_CLASS_NAME='example.servlet.ServletMain'

nohup sh ./run-example.sh ${EXAMPLE_PROJECT} ${EXAMPLE_JAR} ${EXAMPLE_CLASS_NAME} >nohup.log 2>&1 &

tail -f nohup.log
