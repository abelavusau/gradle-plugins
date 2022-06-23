package com.abelavusau.build.plugins.utils.p2.xml.handlers

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

abstract class BaseHandler<T>(private val nodeName: String) : DefaultHandler() {
    private val childHandlers: MutableList<BaseHandler<*>> = mutableListOf()

    abstract fun handleAttributes(attributes: Attributes)
    abstract fun createEntity(): T

    open fun addChild(child: BaseHandler<*>) {
        childHandlers.add(child)
    }

    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
        if (nodeName == qName) {
            handleAttributes(attributes)
        }

        childHandlers.forEach {
            it.handleAttributes(attributes)
        }
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        if (nodeName == qName) {
            createEntity()
        }

        childHandlers.forEach {
            it.createEntity()
        }
    }
}