/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.rowcolumn

import androidx.compose.ui.unit.Dp
import at.released.debuglayout.DebugLayoutDefaults.Row

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
