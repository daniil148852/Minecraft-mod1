#!/bin/sh

# Gradle Wrapper для Android/Termux
# Работаем через java из Termux

export GRADLE_USER_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"

exec java -jar "$0/../gradle/wrapper/gradle-wrapper.jar" "$@"
