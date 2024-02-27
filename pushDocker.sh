#!/usr/bin/env bash

docker tag camunda/core:v1 knockjkeee/test:camunda_core
docker push knockjkeee/test:camunda_core
