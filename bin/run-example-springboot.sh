#!/usr/bin/env bash

EXAMPLE_PROJECT='dragonfly-example-springboot'
EXAMPLE_JAR='dragonfly-example-springboot-0.1.jar'

nohup sh ./run-example.sh ${EXAMPLE_PROJECT} ${EXAMPLE_JAR} >nohup.log 2>&1 &

tail -f nohup.log
