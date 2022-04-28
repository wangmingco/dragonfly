#!/usr/bin/env bash

EXAMPLE_PROJECT=$1
EXAMPLE_JAR=$2
EXAMPLE_CLASS_NAME=$3
EXAMPLE_JVM_PARAM=$4

CLASS_DUMP_DIR=classesdump
AGENT_JAR=dragonfly-agent-jar-0.1.jar
EXAMPLE_AGENT=-javaagent:./${AGENT_JAR}=mock
BYTE_BUDDY_PARAMS=-Dnet.bytebuddy.dump=./${CLASS_DUMP_DIR}
DEBUG_PARAMS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=12009

echo "构建环境---->"
mvn -v

echo "参数信息---->"
echo "EXAMPLE_PROJECT: ${EXAMPLE_PROJECT}"
echo "EXAMPLE_JAR: ${EXAMPLE_JAR}"
echo "EXAMPLE_CLASS_NAME: ${EXAMPLE_CLASS_NAME}"
echo "EXAMPLE_JVM_PARAM: ${EXAMPLE_JVM_PARAM}"
echo "BYTE_BUDDY_PARAMS: ${BYTE_BUDDY_PARAMS}"
echo "EXAMPLE_AGENT: ${EXAMPLE_AGENT}"
echo "DEBUG_PARAMS: ${DEBUG_PARAMS}"

echo "开始构建---->"
cd ..
mvn clean
mvn dependency:purge-local-repository -DreResolve=false -DmanualInclude="co.wangming"
mvn install

cd ./dragonfly-agent

cp ./dragonfly-agent-jar/target/${AGENT_JAR} ./dragonfly-agent-examples/${EXAMPLE_PROJECT}/target/${AGENT_JAR}

cd ./dragonfly-agent-examples/${EXAMPLE_PROJECT}/target

echo "工作目录:"`pwd`
mkdir ${CLASS_DUMP_DIR}

if [ -z "${EXAMPLE_CLASS_NAME}" ]; then
  echo "执行命令: java ${EXAMPLE_AGENT} ${EXAMPLE_JVM_PARAM} ${BYTE_BUDDY_PARAMS} -jar ./${EXAMPLE_JAR}"

  java ${EXAMPLE_AGENT} ${EXAMPLE_JVM_PARAM} ${BYTE_BUDDY_PARAMS} -jar ./${EXAMPLE_JAR}
else
  echo "执行命令: java ${EXAMPLE_AGENT} ${EXAMPLE_JVM_PARAM} ${BYTE_BUDDY_PARAMS} -cp ./${EXAMPLE_JAR} ${EXAMPLE_CLASS_NAME}"

  java ${EXAMPLE_AGENT} ${BYTE_BUDDY_PARAMS} -cp ./${EXAMPLE_JAR} ${EXAMPLE_CLASS_NAME}
fi
