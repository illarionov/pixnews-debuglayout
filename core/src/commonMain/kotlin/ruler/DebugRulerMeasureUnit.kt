/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.ruler

import at.released.debuglayout.DisplayMetrics
import at.released.debuglayout.ruler.DebugRulerMeasureUnit.Unit.DP
import at.released.debuglayout.ruler.DebugRulerMeasureUnit.Unit.INCHES
import at.released.debuglayout.ruler.DebugRulerMeasureUnit.Unit.MM
import at.released.debuglayout.ruler.DebugRulerMeasureUnit.Unit.PX

public data class DebugRulerMeasureUnit(
    val value: Int,
    val units: Unit,
) {
    override fun toString(): String = "$value.$units"
    public enum class Unit {
        /**
         * Device-independent pixels (dp)
         */
        DP,

        /**
         * Pixels (Px)
         */
        PX,

        /**
         * Millimeters
         */
        MM,

        /**
         * Inches
         */
        INCHES,
    }

    public companion object {
        public val ZERO: DebugRulerMeasureUnit = DebugRulerMeasureUnit(0, PX)
    }
}

public val Int.rulerPx: DebugRulerMeasureUnit get() = DebugRulerMeasureUnit(this, PX)

public val Int.rulerMm: DebugRulerMeasureUnit get() = DebugRulerMeasureUnit(this, MM)

public val Int.rulerDp: DebugRulerMeasureUnit get() = DebugRulerMeasureUnit(this, DP)

public val Int.rulerInches: DebugRulerMeasureUnit get() = DebugRulerMeasureUnit(this, INCHES)

internal enum class RulerOrientation {
    HORIZONTAL, VERTICAL
}

@Suppress("MagicNumber", "FLOAT_IN_ACCURATE_CALCULATIONS")
internal fun DebugRulerMeasureUnit.toPx(displayMetrics: DisplayMetrics, orientation: RulerOrientation) = when (units) {
    DP -> value * displayMetrics.density
    PX -> value.toFloat()
    MM -> value * displayMetrics.exactDpi(orientation) / 25.4f
    INCHES -> value * displayMetrics.exactDpi(orientation)
}

private fun DisplayMetrics.exactDpi(
    orientation: RulerOrientation,
) = if (orientation == RulerOrientation.VERTICAL) ydpi else xdpi
