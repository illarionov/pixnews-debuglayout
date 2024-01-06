/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS", "MagicNumber")

package ru.pixnews.debuglayout.rowcolumn

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import ru.pixnews.debuglayout.DebugLayer

internal class ColumnsLayer constructor(
    val columns: RowsColumnsCount,
    val color: Color,
    val arrangement: DebugColumnsArrangement,
) : DebugLayer {
    private val arrangementCalculator: ArrangementCalculator = ArrangementCalculator(arrangement, columns)

    override fun ContentDrawScope.draw() {
        val columns = with(arrangementCalculator) {
             arrange(size.width)
        }

        for (column in columns) {
            drawRect(
                color,
                topLeft = Offset(x = column.start, y = 0f),
                size = Size(
                    width = column.size,
                    height = size.height,
                ),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is ColumnsLayer) {
            return false
        }
        if (columns != other.columns) {
            return false
        }
        if (color != other.color) {
            return false
        }
        if (arrangement != other.arrangement) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = columns.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + arrangement.hashCode()
        return result
    }
}
