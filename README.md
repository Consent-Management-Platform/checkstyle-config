# checkstyle-config
This package contains shared checkstyle config files for use across Java projects.

## Usage

### Step 1: Prerequisite GitHub credentials set-up
Follow [GitHub's "Managing your personal access tokens" guide](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) to set up a personal access token that has `repo` and `read:packages` permissions.

Set up a `GITHUB_USERNAME` environment variable matching your GitHub username.

Set up a `GITHUB_TOKEN` environment variable matching your GitHub personal access token.

### Step 2: Gradle build file set-up

Merge the following code into your build.gradle.kts file:

```kotlin
plugins {
    id("com.consentframework.consentmanagement.checkstyle-config") version "1.0.11-alpha"
}

repositories {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Consent-Management-Platform/checkstyle-config")
            credentials {
                username = project.findProperty("gpr.usr") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    gradlePluginPortal()
}
```

Merge the following into your settings.gradle.kts file:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/Consent-Management-Platform/checkstyle-config")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_USERNAME")
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
        maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention").version("0.9.0")
}
```

### Step 3: Validate gradle builds run checkstyle

Run `gradle build` and validate that:
1. config/checkstyle/checkstyle.xml and config/checkstyle/suppressions.xml files are created
2. The build runs checkstyleMain and checkstyleTest tasks
3. If you have any Java source files with unused imports, the checkstyle tasks fail with relevant checkstyle violation errors

## Technologies
[Checkstyle](https://checkstyle.org/) is a development tool that can be used to automatically validate whether Java code adheres to specified code style rules, which reduces the effort to maintain consistent coding standards.

[GitHub Packages](https://docs.github.com/en/packages) is used to publish the Checkstyle config files for consumption by other Java projects.

[Gradle](https://docs.gradle.org) is used to build the project and manage package dependencies.

## License
The code in this project is released under the [GPL-3.0 License](LICENSE).
