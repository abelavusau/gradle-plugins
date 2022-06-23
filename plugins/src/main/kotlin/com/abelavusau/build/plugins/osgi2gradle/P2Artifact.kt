package com.abelavusau.build.plugins.osgi2gradle

data class P2Artifact(val id: String, val classifier: String, val version: String, val md5: String) {
    private val delimiter = "_"
    override fun toString(): String {
        return "$id$delimiter$version.jar"
    }
}
