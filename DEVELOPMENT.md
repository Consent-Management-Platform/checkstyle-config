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

## Publishing release artifacts to GitHub Packages
Follow [GitHub's "Managing your personal access tokens" guide](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) to set up a personal access token that has write permissions to your GitHub Packages.

Set up a `GITHUB_USERNAME` environment variable matching your GitHub username.

Set up a `GITHUB_TOKEN` environment variable matching your GitHub personal access token.

Increment the package's Maven package version in `build.gradle.kts`.

Run `./gradlew clean build` to ensure build artifacts are based on the latest Smithy models.

Run `./gradlew publish` to publish the new GitHub Package version.

Ref: https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages
