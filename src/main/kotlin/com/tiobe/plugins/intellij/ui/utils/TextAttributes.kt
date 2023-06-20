package com.tiobe.plugins.intellij.ui.utils

import com.intellij.ui.JBColor
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.SimpleTextAttributes.STYLE_BOLD
import java.awt.Color

object TextAttributes {
    val ORANGE_BOLD_ATTRIBUTES =
        SimpleTextAttributes(STYLE_BOLD, JBColor("Dark Orange", Color(255, 125, 0)))
}