package com.tiobe.plugins.intellij.utilities

import com.fasterxml.jackson.dataformat.xml.XmlMapper

object TicsXmlReader {

    inline fun <reified T> readXml(file: String): T {
        return XmlMapper().readValue(file, T::class.java)
    }

    fun getXmlPath(): String {
        return System.getProperty("java.io.tmpdir") + "/tics.xml"
    }
}