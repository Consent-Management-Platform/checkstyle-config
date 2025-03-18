package com.consentframework.consentmanagement

import org.gradle.testkit.runner.TaskOutcome
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.File

class CheckstyleConfigPluginTest {
    @Test
    fun `test plugin applies checkstyle configuration`() {
        val projectDir = File("src/test/resources/test-project-without-errors")
        val gradleRunner = GradleRunner.create()
            .withArguments("--rerun-tasks", "checkstyleMain")
            .withProjectDir(projectDir)
            .withPluginClasspath()
        val result: BuildResult = gradleRunner.build()
        val checkstyleMainTask = result.task(":checkstyleMain")
        assertNotNull(checkstyleMainTask, "checkstyleMainTask should be found")
        assertEquals(TaskOutcome.SUCCESS, checkstyleMainTask?.outcome)
    }
}
