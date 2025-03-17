plugins {
  java
  `maven-publish`
}

repositories {
  mavenCentral()
}

// Expose the Checkstyle config files
configurations {
    create("checkstyleConfig") {
        isVisible = true
        isTransitive = false
    }
}

artifacts {
    add("checkstyleConfig", file("src/main/resources/checkstyle.xml"))
    add("checkstyleConfig", file("src/main/resources/suppressions.xml"))
}

// Publish jar to GitHub Packages so can import into other repositories
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

  publications {
    register<MavenPublication>("gpr") {
      groupId = "com.consentframework.consentmanagement"
      artifactId = "checkstyle-config"
      version = "0.2.0"

      from(components["java"])
    }
  }
}

tasks.named("clean") {
  delete("$rootDir/build")
}
