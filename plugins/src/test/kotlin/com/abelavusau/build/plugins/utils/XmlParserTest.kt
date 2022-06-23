package com.abelavusau.build.plugins.utils

import com.abelavusau.build.plugins.utils.p2.xml.P2RepositoryDescriptorParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File


internal class XmlParserTest {
    private val p2RepositoryDescriptorParser =
        P2RepositoryDescriptorParser(File(this.javaClass.getResource("/artifacts.xml").file))
    private val checksums = listOf(
        "3115c71016ab98c12ad25048d1181ebc",
        "3747bfe95863312b4e6dfc4ad1636860",
        "a7ea4dea16d7c67680af41e78c7814fd",
        "5e4b6bd1246532a78639a44cfdf56ccd"
    )

    @Test
    fun readXml() {
        val artifacts = p2RepositoryDescriptorParser.getOsgiBundles()
        artifacts.forEach {
            println(it.md5)
            Assertions.assertTrue(it.md5 in checksums)
        }
    }
}