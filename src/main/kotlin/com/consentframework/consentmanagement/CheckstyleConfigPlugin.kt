package com.consentframework.consentmanagement

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension
import java.io.File
import java.net.URI

class CheckstyleConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Apply the Checkstyle plugin
        project.plugins.apply("checkstyle")

        // Configure repositories
        project.repositories.maven { repo ->
            repo.setUrl(URI("https://repo.gradle.org/gradle/libs-releases"))
        }

        // Define the target directory for Checkstyle configurations in the client project
        val targetDir = project.layout.projectDirectory.file("config/checkstyle").asFile

        // Copy Checkstyle resources from the plugin's JAR file to the client's target directory
        copyCheckstyleResources(targetDir, project)

        // Configure Checkstyle settings
        configureCheckstyleExtension(project, targetDir)
    }

    private fun copyCheckstyleResources(targetDir: File, project: Project) {
        // Locate resources inside the plugin's JAR file
        val classLoader = this::class.java.classLoader
        val resources = listOf(
            "checkstyle.xml" to File(targetDir, "checkstyle.xml"),
            "suppressions.xml" to File(targetDir, "suppressions.xml")
        )

        resources.forEach { (resourceName, targetFile) ->
            classLoader.getResourceAsStream(resourceName)?.use { inputStream ->
                // Ensure the target directory exists
                targetDir.mkdirs()

                // Copy resource file to the target directory
                targetFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                project.logger.lifecycle("Copied $resourceName to ${targetFile.absolutePath}")
            } ?: project.logger.warn("Resource $resourceName not found")
        }
    }

    private fun configureCheckstyleExtension(project: Project, targetDir: File) {
        val checkstyleExtension = project.extensions.getByType(CheckstyleExtension::class.java)
        checkstyleExtension.toolVersion = "10.16.0"
        checkstyleExtension.isIgnoreFailures = false
        checkstyleExtension.configFile = File(targetDir, "checkstyle.xml")
        checkstyleExtension.configProperties = mapOf(
            "checkstyle.config.dir" to targetDir,
            "checkstyle.suppressions.file" to File(targetDir, "suppressions.xml")
        )
    }
}
