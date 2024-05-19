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

internal class RowsLayer(
    val rowsColumnCount: RowsColumnsCount,
    val color: Color,
    val arrangement: DebugRowsArrangement,
) : DebugLayer {
    private val arrangementCalculator: ArrangementCalculator = ArrangementCalculator(arrangement, rowsColumnCount)

    override fun ContentDrawScope.draw() {
        val columns = with(arrangementCalculator) {
            arrange(size.height)
        }

        for (column in columns) {
            drawRect(
                color,
                topLeft = Offset(x = 0f, y = column.start),
                size = Size(
                    width = size.width,
                    height = column.size,
                ),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is RowsLayer) {
            return false
        }
        if (rowsColumnCount != other.rowsColumnCount) {
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
        var result = rowsColumnCount.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + arrangement.hashCode()
        return result
    }
}
