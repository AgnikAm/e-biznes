FROM ubuntu:22.04

RUN apt update && apt install -y \
    openjdk-8-jdk \
    curl \
    unzip \
    zip \
    wget \
    && apt clean

ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"
ENV PATH="$JAVA_HOME/bin:$PATH"

# Kotlin
RUN curl -sSL https://github.com/JetBrains/kotlin/releases/download/v1.9.22/kotlin-compiler-1.9.22.zip -o kotlin.zip \
    && unzip kotlin.zip -d /opt/kotlin \
    && rm kotlin.zip

ENV PATH="/opt/kotlin/kotlinc/bin:$PATH"

# Gradle
RUN wget -q https://services.gradle.org/distributions/gradle-8.7-bin.zip -O gradle.zip \
    && unzip gradle.zip -d /opt/ \
    && rm gradle.zip

ENV GRADLE_HOME="/opt/gradle-8.7"
ENV PATH="$GRADLE_HOME/bin:$PATH"

WORKDIR /app

# Initialize Gradle project with Hello World
RUN mkdir -p /app/src/main/java/com/example

RUN cat <<EOF > build.gradle
apply plugin: 'java'
apply plugin: 'application'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.xerial:sqlite-jdbc:3.45.3.0'
}

mainClassName = 'com.example.Main'

tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8'
}
EOF

RUN cat <<EOF > /app/src/main/java/com/example/Main.java
package com.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
EOF

RUN gradle build

CMD gradle run
