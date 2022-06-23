package com.abelavusau.build.plugins.utils.p2.xml

import com.abelavusau.build.plugins.osgi2gradle.P2Artifact
import com.abelavusau.build.plugins.utils.p2.xml.handlers.ArtifactHandler
import java.io.File
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class P2RepositoryDescriptorParser(private val descriptorFile: File) {
    private val saxParserFactory: SAXParserFactory = SAXParserFactory.newInstance()
    private val saxParser: SAXParser = saxParserFactory.newSAXParser()
    private val artifactHandler: ArtifactHandler = ArtifactHandler()

    fun getOsgiBundles(): List<P2Artifact> {
        saxParser.parse(descriptorFile, artifactHandler)
        return artifactHandler.artifacts.filter { it.classifier == "osgi.bundle" }
    }
}