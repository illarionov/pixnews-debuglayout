/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.rowcolumn

import kotlin.jvm.JvmInline

@JvmInline
public value class RowsColumnsCount(
    public val value: Int,
) {
    public companion object {
        public val Auto: RowsColumnsCount = RowsColumnsCount(0)
    }
}

public val Int.asRowColumnCount: RowsColumnsCount
    get() = RowsColumnsCount(this)
