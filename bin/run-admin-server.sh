#!/usr/bin/env bash

cd ..
mvn clean install

cd ./dragonfly-admin-server/target
java -jar ./dragonfly-admin-server-0.1.jar
