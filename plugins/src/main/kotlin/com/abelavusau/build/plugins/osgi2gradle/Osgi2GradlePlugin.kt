package com.abelavusau.build.plugins.osgi2gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File

class Osgi2GradlePlugin : Plugin<Project> {
    override fun apply(p: Project) {
        try {
            val extension = p.extensions.create("osgi2gradle", Osgi2GradlePluginExtension::class.java)
            val analyzer = ManifestAnalyzer(p)
            p.afterEvaluate {
                val requireBundles = analyzer.getAttributeValues(ManifestAnalyzer.REQUIRE_BUNDLE)
                val importPackages = analyzer.getAttributeValues(ManifestAnalyzer.IMPORT_PACKAGE)
                val bundles = mutableListOf<String>()
                requireBundles?.let { bundles.addAll(requireBundles) }
                importPackages?.let { bundles.addAll(importPackages) }
                val p2repo = extension.p2LocalRepository.get()
                val eclipseP2Repo = extension.downloadEclipseDirectory.get() + "/plugins"

                println("P2 repo is $p2repo")

                val p2Jars = File(p2repo).listFiles { _, name ->
                    !name.contains(".source_")
                }?.associate { it.nameWithoutExtension.substringBefore("_") to it }

                val eclipseP2Jars = File(eclipseP2Repo).listFiles { _, name ->
                    !name.contains(".source_")
                }?.associate { it.nameWithoutExtension.substringBefore("_") to it }

                bundles.let {
                    for (entry in it) {
                        var bundle = entry
                        var found = false

                        if (entry.contains("bundle-version")) {
                            //  org.eclipse.e4.ui.model.workbench;bundle-version="1.1.100",
                            bundle = entry.substringBefore(";")
                            val version = entry.substringAfter(";bundle-version=").removeSurrounding("\"", "\"")
                            println("Bundle $bundle version: $version")
                        }

                        if (entry.contains("resolution:=")) {
                            bundle = entry.substringBefore(";")
                        }

                        println("Dependency found: ${bundle}")

                        val p2Jar = p2Jars?.get(bundle)

                        if (p2Jar != null) {
                            println("Dependency included via 'files(${p2Jar.path})'")
                            p.dependencies.add("implementation", p.files(p2Jar.absolutePath))
                            found = true
                        }

                        if (!found) {
                            val eclipseP2Jar = eclipseP2Jars?.get(bundle)

                            if (eclipseP2Jar != null) {
                                println("Dependency included via 'files(${eclipseP2Jar.path})'")
                                p.dependencies.add("implementation", p.files(eclipseP2Jar.absolutePath))
                                found = true
                            }
                        }

                        if (!found) {
                            // If not found in the P2 repo, including as a project dependency with a module name
                            println("Dependency included via 'project($bundle)'")
                            p.dependencies.add("implementation", p.rootProject.project(bundle))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("WARN: Unable to parse manifest file in module ${p.name}, $e")
        }
    }
}

abstract public class Osgi2GradlePluginExtension {
    abstract public val p2LocalRepository: Property<String>
    abstract public val eclipseRemoteRepository: Property<String>
    abstract public val downloadEclipseDirectory: Property<String>
}