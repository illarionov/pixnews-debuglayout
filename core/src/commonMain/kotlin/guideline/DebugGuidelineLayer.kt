/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")

package ru.pixnews.debuglayout.guideline

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import ru.pixnews.debuglayout.DebugLayer
import ru.pixnews.debuglayout.guideline.DebugGuidelineOffset.Percent
import ru.pixnews.debuglayout.guideline.DebugGuidelinePosition.Bottom
import ru.pixnews.debuglayout.guideline.DebugGuidelinePosition.CenterHorizontal
import ru.pixnews.debuglayout.guideline.DebugGuidelinePosition.CenterVertical
import ru.pixnews.debuglayout.guideline.DebugGuidelinePosition.End
import ru.pixnews.debuglayout.guideline.DebugGuidelinePosition.Start
import ru.pixnews.debuglayout.guideline.DebugGuidelinePosition.Top

internal class DebugGuidelineLayer(
    private val guideline: DebugGuidelinePosition,
    private val color: Color,
    private val strokeWidth: Dp,
) : DebugLayer {
    override fun ContentDrawScope.draw() {
        val halfStrokeWidth: Float = strokeWidth.toPx() / 2f
        when (guideline) {
            is Top -> drawHorizontalGuideline(
                offsetToPx(guideline.offset, true) - halfStrokeWidth,
            )

            is Bottom -> drawHorizontalGuideline(
                size.height - halfStrokeWidth - offsetToPx(guideline.offset, true),
            )

            is Start -> drawVerticalGuideline(
                offsetToPx(guideline.offset, false) - halfStrokeWidth,
            )

            is End -> drawVerticalGuideline(
                size.width - halfStrokeWidth - offsetToPx(guideline.offset, false),
            )

            is CenterHorizontal -> drawVerticalGuideline(
                size.width / 2f - halfStrokeWidth + guideline.verticalOffset.toPx(),
            )

            is CenterVertical -> drawHorizontalGuideline(
                size.height / 2f - halfStrokeWidth + guideline.horizontalOffset.toPx(),
            )
        }
    }

    private fun ContentDrawScope.drawHorizontalGuideline(topOffset: Float) {
        drawLine(
            color = color,
            strokeWidth = strokeWidth.toPx(),
            start = Offset(x = 0f, y = topOffset),
            end = Offset(x = size.width - 1f, y = topOffset),
        )
    }

    private fun ContentDrawScope.drawVerticalGuideline(startOffset: Float) {
        drawLine(
            color = color,
            strokeWidth = strokeWidth.toPx(),
            start = Offset(x = startOffset, y = 0f),
            end = Offset(x = startOffset, y = size.height - 1f),
        )
    }

    private fun ContentDrawScope.offsetToPx(offset: DebugGuidelineOffset, isHorizontalGuideline: Boolean): Float {
        return when (offset) {
            is DebugGuidelineOffset.Dp -> offset.value.toPx()
            is Percent -> offset.value * if (isHorizontalGuideline) size.height else size.width
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is DebugGuidelineLayer) {
            return false
        }
        if (guideline != other.guideline) {
            return false
        }
        if (color != other.color) {
            return false
        }
        if (strokeWidth != other.strokeWidth) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = guideline.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + strokeWidth.hashCode()
        return result
    }
}
