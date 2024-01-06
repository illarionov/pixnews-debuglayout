/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.ruler

import ru.pixnews.debuglayout.ruler.DebugRulerHorizontalZeroPoint.Alignment.LEFT

public data class DebugRulerHorizontalZeroPoint(
    val alignment: Alignment = LEFT,
    val offset: DebugRulerMeasureUnit = DebugRulerMeasureUnit.ZERO,
) {
    public enum class Alignment {
        LEFT, CENTER, RIGHT
    }

    public companion object {
        public val ZERO: DebugRulerHorizontalZeroPoint = DebugRulerHorizontalZeroPoint(LEFT, DebugRulerMeasureUnit.ZERO)
    }
}
