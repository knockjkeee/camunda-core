#!/usr/bin/env bash

if ! command -v mvn -version &> /dev/null
then
    sudo apt-get install maven
fi

mvn clean install

docker build . --tag=camunda/core:v1

docker tag camunda/core:v1 knockjkeee/test:camunda_lates
docker push knockjkeee/test:camunda_lates
