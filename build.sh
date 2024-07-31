#!/bin/bash

scriptsFolder=$(dirname "${BASH_SOURCE}")
cd $scriptsFolder
projectPath=$(pwd)

cd $projectPath/taxi-dispatcher
mvn clean package &

cd $projectPath/taxi-agent
mvn clean package &

wait