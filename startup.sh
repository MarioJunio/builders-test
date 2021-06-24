#! /bin/bash
./gradlew clean build
cd ./devops/local/
docker-compose up -d