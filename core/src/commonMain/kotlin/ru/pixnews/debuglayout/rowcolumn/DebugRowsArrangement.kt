/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.rowcolumn

import androidx.compose.ui.unit.Dp
import ru.pixnews.debuglayout.DebugLayoutDefaults.Row

public sealed class DebugRowsArrangement {
    public data class Top(
        val height: Dp,
        val offset: Dp = Row.offset,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()

    public data class Bottom(
        val height: Dp,
        val offset: Dp = Row.offset,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()

    public data class Center(
        val height: Dp,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()

    public data class Stretch(
        val margin: Dp = Row.margin,
        val gutter: Dp = Row.gutter,
    ) : DebugRowsArrangement()
}
