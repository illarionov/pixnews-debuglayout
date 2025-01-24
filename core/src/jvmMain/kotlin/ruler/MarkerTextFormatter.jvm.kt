/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.ruler

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

internal actual operator fun MarkerTextFormatter.Companion.invoke(): MarkerTextFormatter = JvmMarkerTextFormatter()

// XXX: Keep in sync with Android implementation
private class JvmMarkerTextFormatter : MarkerTextFormatter {
    val decimalFormat = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ROOT)).apply {
        maximumFractionDigits = 1
    }
    override fun format(value: Float): String = decimalFormat.format(value + 0f)
}
