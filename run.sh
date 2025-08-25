#!/bin/bash

#
# Application Execution Script
#
# Responsibilities:
# - Cleans previous builds
# - Compiles fresh sources
# - Executes the main application class
# - Provides one-command startup
# - Can be extended for different environments
#
# Usage: ./run.sh [debug|prod]

#!/bin/bash
set -e

cd "$(dirname "$0")" || exit 1

echo "Building and starting main application..."
mvn clean compile exec:java -Dexec.mainClass="com.elisz.playerdemo.App"
