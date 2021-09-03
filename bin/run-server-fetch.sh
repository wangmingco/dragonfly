#!/usr/bin/env bash

cd ..
mvn clean install

cd ./dragonfly-server/dragonfly-server-fetch/target

java -jar dragonfly-server-fetch-0.1.jar
