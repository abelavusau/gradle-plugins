package com.abelavusau.build.plugins.utils.p2.xml.handlers

import org.xml.sax.Attributes

class PropertiesHandler : BaseHandler<String>("property") {
    var chekcsum: String = ""

    override fun handleAttributes(attributes: Attributes) {
        if ("download.md5" == attributes.getValue("name")) {
            chekcsum = attributes.getValue("value")
        }
    }

    override fun createEntity(): String {
        return chekcsum
    }
}