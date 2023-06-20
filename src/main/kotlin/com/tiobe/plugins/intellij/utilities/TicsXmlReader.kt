package com.tiobe.plugins.intellij.utilities

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import java.nio.file.Paths

object TicsXmlReader {

    inline fun <reified T> readXml(file: String): T {
        return XmlMapper().readValue(file, T::class.java)
    }

    fun getXmlPath(): String {
        return Paths.get(System.getProperty("java.io.tmpdir") + "/tics.xml").normalize().toString()
    }
}