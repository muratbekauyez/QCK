#!/bin/bash

# Clean and build the Maven project
mvn clean install

java -jar ./target/diploma.jar
