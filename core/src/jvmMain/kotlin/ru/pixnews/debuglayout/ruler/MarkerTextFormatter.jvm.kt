/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.ruler

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

internal actual operator fun MarkerTextFormatter.Companion.invoke(): MarkerTextFormatter = JvmMarkerTextFormatter()

private class JvmMarkerTextFormatter : MarkerTextFormatter {
    val decimalFormat = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ROOT)).apply {
        maximumFractionDigits = 1
    }
    override fun format(value: Float): String = decimalFormat.format(value)
}
