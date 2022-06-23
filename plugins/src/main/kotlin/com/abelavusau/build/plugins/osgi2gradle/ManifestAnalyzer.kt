package com.abelavusau.build.plugins.osgi2gradle

import org.gradle.api.Project
import java.io.File
import java.util.jar.Attributes
import java.util.jar.Manifest

class ManifestAnalyzer(project: Project, manifestFilePath: String = "META-INF/MANIFEST.MF") {
    companion object {
        val REQUIRE_BUNDLE = "Require-Bundle"
        val IMPORT_PACKAGE = "Import-Package"
    }

    private var attributes: Attributes = Attributes()

    init {
        try {
            var file = File(manifestFilePath)

            if (!file.exists() || !file.isAbsolute) {
                val path : String = project.projectDir.absolutePath + "/" + manifestFilePath
                file = File(path)
            }

            if (file.exists()) {
                System.out.println("File : ${file.absolutePath}")
                val manifest = Manifest(file.inputStream())
                this.attributes = manifest.mainAttributes
            } else {
                System.out.println("File : ${file.absolutePath} does not exist")
            }
        } catch (e: Exception) {
            throw RuntimeException("Unable to parse manifest file in module ${project.name}", e)
        }
    }

    fun getAttributeValues(attribute: String): List<String>? {
        return this.attributes.getValue(attribute)?.let { s: String -> s.split(",") }
    }
}