#!/usr/bin/env bash

EXAMPLE_PROJECT=$1
EXAMPLE_JAR=$2
EXAMPLE_CLASS_NAME=$3

echo "EXAMPLE_PROJECT: ${EXAMPLE_PROJECT}"
echo "EXAMPLE_JAR: ${EXAMPLE_JAR}"
echo "EXAMPLE_CLASS_NAME: ${EXAMPLE_CLASS_NAME}"

cd ..
mvn clean
mvn install

cd ./dragonfly-agent

cp ./dragonfly-agent-jar/target/dragonfly-agent-jar-0.1.jar ./dragonfly-agent-examples/${EXAMPLE_PROJECT}/target/dragonfly-agent-jar-0.1.jar

cd ./dragonfly-agent-examples/${EXAMPLE_PROJECT}/target

if [ -z "${EXAMPLE_CLASS_NAME}" ]; then
  java -javaagent:./dragonfly-agent-jar-0.1.jar -jar ./${EXAMPLE_JAR}
else
  java -javaagent:./dragonfly-agent-jar-0.1.jar -cp ./${EXAMPLE_JAR} ${EXAMPLE_CLASS_NAME}
fi
