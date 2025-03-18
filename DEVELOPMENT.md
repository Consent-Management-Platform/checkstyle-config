# Development

## Building the project

### First-time set-up
To build the project for the first time, run

```sh
./gradlew build
```

and validate that it completes successfully.

After a successful build, `checkstyle.xml` and `suppressions.xml` should be present in `./build/resources/main/`.

### Subsequent builds
In order to clean up stale build artifacts and rebuild the package based on your latest changes, run

```sh
./gradlew clean build
```

If you do not clean before building, your local environment may continue to use stale, cached versions of the checkstyle config files in builds.

## Publishing new plugin versions to Gradle Plugin Portal
Follow the [Gradle Plugin Portal guide](https://plugins.gradle.org/docs/publish-plugin) to set up an API key with permissions to publish plugins.

Increment the package's Maven package version in `build.gradle.kts`.

Run `./gradlew clean build` to ensure build artifacts are based on the latest Smithy models.

Run `./gradlew publishPlugins` to publish the new Gradle plugin version to Gradle Plugin Portal.
