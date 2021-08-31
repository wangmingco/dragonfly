#!/usr/bin/env bash

cd ..
mvn clean install

cp ./dragonfly-agent/target/dragonfly-agent-0.1.jar ./dragonfly-agent-examples/dragonfly-example-simple/target/dragonfly-agent-0.1.jar

cd ./dragonfly-agent-examples/dragonfly-example-simple/target
java -javaagent:./dragonfly-agent-0.1.jar -cp ./dragonfly-example-simple-0.1.jar co.wangming.dragonfly.example.simple.ExampleMain
