pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention").version("0.9.0")
}

rootProject.name = "checkstyle-config"
