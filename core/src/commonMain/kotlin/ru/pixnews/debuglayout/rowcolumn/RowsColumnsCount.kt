/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.rowcolumn

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
