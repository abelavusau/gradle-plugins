package com.abelavusau.build.plugins.utils.p2.xml.handlers

import com.abelavusau.build.plugins.osgi2gradle.P2Artifact
import org.xml.sax.Attributes


class ArtifactHandler : BaseHandler<P2Artifact>("artifact") {
    val artifacts = ArrayList<P2Artifact>()

    private lateinit var id: String
    private lateinit var classifier: String
    private lateinit var version: String
    private val child: PropertiesHandler = PropertiesHandler()

    init {
        addChild(child)
    }

    override fun handleAttributes(attributes: Attributes) {
        id = attributes.getValue("id")
        classifier = attributes.getValue("classifier")
        version = attributes.getValue("version")
    }

    override fun createEntity(): P2Artifact {
        val artifact = P2Artifact(id, classifier, version, child.chekcsum)
        artifacts.add(artifact)
        return artifact
    }
}