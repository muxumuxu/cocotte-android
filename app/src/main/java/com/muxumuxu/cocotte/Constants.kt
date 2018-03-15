package com.muxumuxu.cocotte

import android.graphics.Color

val colors = arrayOf("#484291", "#FFDB3A", "#007CFF", "#9F57B7", "#99D22A", "#FFDA3A",
        "#99D22A", "#484291", "#FFDA3A", "#9F57B6", "#FFDA3A", "#99D22A")

fun getCategoryColor(index: Int): Int {
    return Color.parseColor(colors[index])
}

const val contactEmail = "bonjour@cocotte-app.com"