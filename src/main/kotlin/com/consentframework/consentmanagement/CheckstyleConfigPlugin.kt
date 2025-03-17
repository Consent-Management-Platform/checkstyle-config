package com.consentframework.consentmanagement

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.tasks.Copy
import java.net.URI

class CheckstyleConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Apply the Checkstyle plugin
        project.plugins.apply("checkstyle")

        // Configure repositories
        project.repositories.maven { repo ->
            repo.setUrl(URI("https://repo.gradle.org/gradle/libs-releases"))
        }
        project.repositories.maven { repo ->
            repo.setUrl(URI("https://maven.pkg.github.com/Consent-Management-Platform/checkstyle-config"))
            repo.credentials {
                it.username = project.findProperty("gpr.usr") as String? ?: System.getenv("GITHUB_USERNAME")
                it.password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }

        // Declare a Checkstyle dependency configuration to simplify referencing its config files
        project.configurations.create("checkstyleConfig")

        // Add dependencies
        project.dependencies.add("checkstyleConfig", "com.consentframework.consentmanagement:checkstyle-config:1.0.12")

        // Create a task to download the Checkstyle config files
        project.tasks.register("downloadCheckstyleConfig", Copy::class.java) { task ->
            val checkstyleConfigFiles = project.zipTree(
                project.configurations.getByName("checkstyleConfig")
                    .files
                    .first { it.name.contains("checkstyle-config") }
            )
            task.doFirst {
                println("Files found in checkstyleConfig:")
                checkstyleConfigFiles.forEach { println(it.name) }
            }
            task.from(checkstyleConfigFiles)
            task.into(project.layout.projectDirectory.file("config/checkstyle"))
            task.include("checkstyle.xml")
            task.include("suppressions.xml")
        }

        // Download Checkstyle config files before running Checkstyle tasks
        project.tasks.withType(org.gradle.api.plugins.quality.Checkstyle::class.java).configureEach { task ->
            task.dependsOn("downloadCheckstyleConfig")
        }

        // Clear downloaded Checkstyle config files when users run "gradle clean"
        project.tasks.named("clean") { task ->
            task.doLast {
                project.delete(project.layout.projectDirectory.file("config/checkstyle"))
            }
        }

        // Configure Checkstyle settings
        val checkstyleExtension = project.extensions.getByType(CheckstyleExtension::class.java)
        checkstyleExtension.toolVersion = "10.16.0"
        checkstyleExtension.isIgnoreFailures = false
        checkstyleExtension.configFile = project.layout.projectDirectory.file("config/checkstyle/checkstyle.xml").asFile
        checkstyleExtension.configProperties = mapOf(
            "checkstyle.config.dir" to project.layout.projectDirectory.file("config/checkstyle").asFile,
            "checkstyle.suppressions.file" to project.layout.projectDirectory.file("config/checkstyle/suppressions.xml").asFile
        )
    }
}
