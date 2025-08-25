#!/bin/bash
set -e

cd "$(dirname "$0")" || exit 1

echo "Starting Initiator..."
mvn clean compile exec:java -Dexec.mainClass="com.elisz.playerdemo.InitiatorApp"