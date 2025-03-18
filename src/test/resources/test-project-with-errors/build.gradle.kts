plugins {
    java
    checkstyle

    id("com.consentframework.consentmanagement.checkstyle-config") version "1.2.1"
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.build.configure {
    dependsOn(tasks.checkstyleMain)
}
