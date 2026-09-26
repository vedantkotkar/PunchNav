#!/bin/sh
APP_BASE_NAME=`basename "$0"`
APP_HOME="`pwd -P`"
DEFAULT_JVM_OPTS=""

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
else
    JAVACMD="java"
fi

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Download wrapper jar if missing
if [ ! -r "$CLASSPATH" ]; then
    echo "Downloading gradle-wrapper.jar..." >&2
    mkdir -p "$APP_HOME/gradle/wrapper"
    curl --location --silent --fail --output "$CLASSPATH" "https://raw.githubusercontent.com/gradle/gradle/v8.5.0/gradle/wrapper/gradle-wrapper.jar"
fi

exec "$JAVACMD" $DEFAULT_JVM_OPTS -jar "$CLASSPATH" "$@"
