package com.abelavusau.build.plugins.osgi2gradle

data class Dependency(val artifactId: String, val groupId: String, val version: String = "") {
    override fun toString(): String {
        version.ifEmpty {
            return "$groupId:$artifactId"
        }

        return "$groupId:$artifactId:$version"
    }
}
