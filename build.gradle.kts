plugins {
  `java-gradle-plugin`
  `maven-publish`
  kotlin("jvm") version "2.1.10"
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
version = "1.0.12"

gradlePlugin {
  kotlin {
    jvmToolchain {
      (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  plugins {
    create("checkstyle-config") {
      id = "$group.checkstyle-config"
      implementationClass = "com.consentframework.consentmanagement.CheckstyleConfigPlugin"
    }
  }
}

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/Consent-Management-Platform/checkstyle-config")
      credentials {
        username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
        password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
      }
    }
  }
}
