/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.guideline

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import at.released.debuglayout.DebugLayoutDefaults.Guideline
import at.released.debuglayout.guideline.DebugGuidelineOffset.Percent

public sealed class DebugGuidelinePosition {
    public data class Start(
        val offset: DebugGuidelineOffset = Guideline.horizontalOffset,
    ) : DebugGuidelinePosition()

    public data class End(
        val offset: DebugGuidelineOffset = Guideline.horizontalOffset,
    ) : DebugGuidelinePosition()

    public data class Top(
        val offset: DebugGuidelineOffset = Guideline.verticalOffset,
    ) : DebugGuidelinePosition()

    public data class Bottom(
        val offset: DebugGuidelineOffset = Guideline.verticalOffset,
    ) : DebugGuidelinePosition()

    public data class CenterHorizontal(
        val verticalOffset: Dp = 0.dp,
    ) : DebugGuidelinePosition()

    public data class CenterVertical(
        val horizontalOffset: Dp = 0.dp,
    ) : DebugGuidelinePosition()
}

public sealed class DebugGuidelineOffset {
    public data class Dp(val value: androidx.compose.ui.unit.Dp) : DebugGuidelineOffset()
    public data class Percent(val value: Float) : DebugGuidelineOffset()
}

public inline val Dp.asGuidelineOffset: DebugGuidelineOffset.Dp get() = DebugGuidelineOffset.Dp(value = this)
public inline val Float.asGuidelineOffsetPercent: Percent
    get() = Percent(value = this)
