# checkstyle-config
This package contains shared checkstyle config files for use across Java projects.

## Usage

Merge the following code into your build.gradle.kts file:

```kotlin
plugins {
    id("com.consentframework.consentmanagement.checkstyle-config") version "1.2.1"
}

repositories {
    gradlePluginPortal()
}
```

Merge the following into your settings.gradle.kts file:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
```

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
