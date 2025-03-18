plugins {
  kotlin("jvm") version "2.1.10"

  id("com.gradle.plugin-publish") version "1.3.1"
}

repositories {
  mavenCentral()
  maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
}

// Expose the Checkstyle config files
configurations {
  create("checkstyleConfig") {
    isVisible = true
    isTransitive = false
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  runtimeOnly("org.gradle:gradle-tooling-api:8.12")
}

sourceSets {
  getByName("main") {
    java.srcDirs("src/main/kotlin")
  }
}

artifacts {
  add("checkstyleConfig", file("src/main/resources/checkstyle.xml"))
  add("checkstyleConfig", file("src/main/resources/suppressions.xml"))
}

group = "com.consentframework.consentmanagement"
version = "1.2.0"

gradlePlugin {
  website.set("https://github.com/Consent-Management-Platform/checkstyle-config")
  vcsUrl.set("https://github.com/Consent-Management-Platform/checkstyle-config.git")

  kotlin {
    jvmToolchain {
      (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  plugins {
    create("checkstyle-config") {
      id = "$group.checkstyle-config"
      implementationClass = "com.consentframework.consentmanagement.CheckstyleConfigPlugin"
      displayName = "Checkstyle Config Plugin"
      description = "A Gradle plugin for configuring Checkstyle"
      tags.set(listOf("checkstyle", "gradle-plugin", "consent-management-platform"))
    }
  }
}
