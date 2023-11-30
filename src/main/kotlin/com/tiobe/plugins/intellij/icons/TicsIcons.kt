package com.tiobe.plugins.intellij.icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object TicsIcons {

    @JvmField
    val ANALYZE_FILE: Icon = load("/icons/tics-file-solid.svg")

    @JvmField
    val ANALYZE_FOLDER: Icon = load("/icons/tics-folder-solid.svg")

    @JvmField
    val CANCEL_TICS: Icon = load("/icons/tics-cancel-solid.svg")

    @JvmField
    val CONFIGURE_TICS: Icon = load("/icons/tics-config-solid.svg")

    @JvmField
    val INSTALL_TICS: Icon = load("/icons/tics-download-solid.svg")

    @JvmField
    val TICS_LOGO: Icon = load("/icons/TI_logo.svg")

    private fun load(path: String): Icon {
        return IconLoader.getIcon(path, TicsIcons::class.java)
    }
}