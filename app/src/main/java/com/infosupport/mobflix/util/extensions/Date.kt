package com.infosupport.mobflix.util.extensions

import java.text.DateFormat
import java.util.*

fun Date.toLocalizedString(format: Int): String {
    return DateFormat.getDateInstance(format).format(this)
}
