#!/bin/bash

source checkForJava.sh

if ! checkForJava; then
    echo "** Java is not configured correctly in your environment"
    exit 1
fi

if ! checkForMavenSettings; then
    echo "** Your Maven settings are not configured correctly in your environment"
    exit 1
fi

if [ $# -lt 1 ]; then
  echo "** Missing your student id"
  exit 1
fi


studentId=$1

./mvnw --batch-mode archetype:generate \
  -DinteractiveMode=false \
  -DarchetypeGroupId=edu.pdx.cs410J \
  -DarchetypeArtifactId=student-archetype \
  -DarchetypeVersion=Winter2020 \
  -DgroupId=edu.pdx.cs410J.${studentId} \
  -DartifactId=student \
  -Dpackage=edu.pdx.cs410J.${studentId} \
  -Dversion=Winter2020