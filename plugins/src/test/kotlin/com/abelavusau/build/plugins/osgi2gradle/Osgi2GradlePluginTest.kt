package com.abelavusau.build.plugins.osgi2gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Osgi2GradlePluginTest {

    @Test
    fun apply() {
        val project: Project = ProjectBuilder.builder().build()
        project.getPluginManager().apply("com.abelavusau.build.plugins.osgi2gradle")
        assertTrue(project.plugins.getPlugin("com.abelavusau.build.plugins.osgi2gradle") is Osgi2GradlePlugin)
    }
}