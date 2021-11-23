#!/usr/bin/env bash

EXAMPLE_PROJECT=$1
EXAMPLE_JAR=$2
EXAMPLE_CLASS_NAME=$3
EXAMPLE_JVM_PARAM=$4

AGENT_JAR=dragonfly-agent-jar-0.1.jar
EXAMPLE_AGENT=-javaagent:./${AGENT_JAR}

echo "EXAMPLE_PROJECT: ${EXAMPLE_PROJECT}"
echo "EXAMPLE_JAR: ${EXAMPLE_JAR}"
echo "EXAMPLE_CLASS_NAME: ${EXAMPLE_CLASS_NAME}"
echo "EXAMPLE_JVM_PARAM: ${EXAMPLE_JVM_PARAM}"
echo "EXAMPLE_AGENT: ${EXAMPLE_AGENT}"

cd ..
mvn clean
mvn dependency:purge-local-repository -DreResolve=false -DmanualInclude="co.wangming"
mvn install

cd ./dragonfly-agent

cp ./dragonfly-agent-jar/target/${AGENT_JAR} ./dragonfly-agent-examples/${EXAMPLE_PROJECT}/target/${AGENT_JAR}

cd ./dragonfly-agent-examples/${EXAMPLE_PROJECT}/target

if [ -z "${EXAMPLE_CLASS_NAME}" ]; then
  java ${EXAMPLE_AGENT} ${EXAMPLE_JVM_PARAM} -jar ./${EXAMPLE_JAR}
else
  java ${EXAMPLE_AGENT} ${EXAMPLE_JVM_PARAM} -cp ./${EXAMPLE_JAR} ${EXAMPLE_CLASS_NAME}
fi
