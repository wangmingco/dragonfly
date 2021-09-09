#!/usr/bin/env bash

cd ..
mvn clean
mvn install

cd ./dragonfly-agent

cp ./dragonfly-agent-jar/target/dragonfly-agent-jar-0.1.jar ./dragonfly-agent-examples/dragonfly-example-simple/target/dragonfly-agent-jar-0.1.jar

cd ./dragonfly-agent-examples/dragonfly-example-simple/target
java -javaagent:./dragonfly-agent-jar-0.1.jar -cp ./dragonfly-example-simple-0.1.jar co.wangming.dragonfly.example.simple.ExampleMain
