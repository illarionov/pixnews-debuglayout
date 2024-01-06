/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")

package ru.pixnews.debuglayout

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp

internal class GridLayer(
    private val gridSize: Dp,
    private val color: Color,
    private val strokeWidth: Dp,
) : DebugLayer {
    override fun ContentDrawScope.draw() {
        val strokeWidthPx = strokeWidth.toPx()
        val halfStrokeWidthPx = strokeWidthPx / 2f

        val verticalStep = Offset(x = gridSize.toPx(), y = 0f)
        var verticalStartOffset = Offset(x = -halfStrokeWidthPx, y = 0f)
        var verticalEndOffset = Offset(x = 0f, y = size.height - 1f)

        while (verticalStartOffset.x < size.width) {
            drawLine(
                color = color,
                strokeWidth = strokeWidthPx,
                start = verticalStartOffset,
                end = verticalEndOffset,
            )
            verticalStartOffset += verticalStep
            verticalEndOffset += verticalStep
        }

        val horizontalStep = Offset(x = 0f, y = gridSize.toPx())
        var horizontalStartOffset = Offset(x = 0f, y = -halfStrokeWidthPx)
        var horizontalEndOffset = Offset(x = size.width - 1f, y = 0f)

        while (horizontalStartOffset.y < size.height) {
            drawLine(
                color = color,
                strokeWidth = strokeWidthPx,
                start = horizontalStartOffset,
                end = horizontalEndOffset,
            )
            horizontalStartOffset += horizontalStep
            horizontalEndOffset += horizontalStep
        }
    }

    @Suppress("NO_BRACES_IN_CONDITIONALS_AND_LOOPS")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GridLayer) return false
        if (gridSize != other.gridSize) return false
        if (color != other.color) return false
        if (strokeWidth != other.strokeWidth) return false
        return true
    }

    override fun hashCode(): Int {
        var result = gridSize.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + strokeWidth.hashCode()
        return result
    }
}
