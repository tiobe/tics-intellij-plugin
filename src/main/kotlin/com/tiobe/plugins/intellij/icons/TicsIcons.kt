package com.tiobe.plugins.intellij.icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object TicsIcons {

    @JvmField
    val ANALYZE_FILE: Icon = load("/icons/TI_analyze_file.png")

    @JvmField
    val ANALYZE_FOLDER: Icon = load("/icons/TI_analyze_project.png")

    @JvmField
    val CANCEL_TICS: Icon = load("/icons/TI_cancel_analysis.png")

    @JvmField
    val CONFIGURE_TICS: Icon = load("/icons/TI_configuration.png")

    @JvmField
    val TICS_LOGO: Icon = load("/icons/TI_logo.svg")

    private fun load(path: String): Icon {
        return IconLoader.getIcon(path, TicsIcons::class.java)
    }
}