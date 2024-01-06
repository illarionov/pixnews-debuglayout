/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS")

package ru.pixnews.debuglayout.ruler

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.debuglayout.DebugLayer
import ru.pixnews.debuglayout.DebugLayoutDefaults.Ruler
import ru.pixnews.debuglayout.DisplayMetrics
import ru.pixnews.debuglayout.ruler.RulerOrientation.HORIZONTAL
import ru.pixnews.debuglayout.ruler.Tick.TickType
import ru.pixnews.debuglayout.ruler.Tick.TickType.MAJOR
import ru.pixnews.debuglayout.ruler.Tick.TickType.MINOR
import ru.pixnews.debuglayout.ruler.ZeroOffset.Companion.toCommonZeroOffset

internal class HorizontalRulerLayer(
    private val textMeasurer: TextMeasurer,
    private val displayMetrics: DisplayMetrics,
    private val step: DebugRulerMeasureUnit = Ruler.step,
    private val zeroOffset: DebugRulerHorizontalZeroPoint = DebugRulerHorizontalZeroPoint.ZERO,
    private val height: Dp = Ruler.horizontalRulerHeight,
    private val ticksColor: Color = Ruler.tickColor,
    private val textStyle: TextStyle = Ruler.textStyle,
    private val backgroundColor: Color = Ruler.backgroundColor,
    private val outlineColor: Color = Ruler.outlineColor,
) : DebugLayer {
    private val markerTextFormatter = MarkerTextFormatter()
    private val minWidth = 24.dp
    private val outlineStrokeWidth = 0.5.dp
    private val minorTickHeight = Ruler.minorTickSize
    private val majorTickHeight = Ruler.majorTickSize
    private val tickWidth = Ruler.tickStrokeWidth
    private val bottomLineHeightStyle = LineHeightStyle(
        alignment = Alignment.Bottom,
        trim = Trim.None,
    )
    private val textStyleCenterMarker = this.textStyle.copy(
        textAlign = TextAlign.Center,
        lineHeightStyle = bottomLineHeightStyle,
    )
    private val textStyleLeftMarker = this.textStyle.copy(
        textAlign = TextAlign.Left,
        lineHeightStyle = bottomLineHeightStyle,
    )
    private val textStyleRightMarker = this.textStyle.copy(
        textAlign = TextAlign.Right,
        lineHeightStyle = bottomLineHeightStyle,
    )

    override fun ContentDrawScope.draw() {
        if (size.height < height.toPx() || size.width < minWidth.toPx()) {
            return
        }
        HorizontalRulerDrawer(this).draw()
    }

    private inner class HorizontalRulerDrawer(
        private val drawScope: ContentDrawScope,
    ) {
        val rulerSize: Size
        val stepPx: Float
        val displayMetrics: DisplayMetrics
        val minorTickHeightPx: Float
        val majorTickHeightPx: Float
        val strokeWidthPx: Float
        val markerHalfMaxWidthPx: Float
        val textMaxLeftBorder: Float
        val textYOffset: Float

        init {
            with(drawScope) {
                rulerSize = Size(width = size.width, height = height.toPx())
                displayMetrics = this@HorizontalRulerLayer.displayMetrics.withDensity(density)
                stepPx = step.toPx(displayMetrics, HORIZONTAL)
                minorTickHeightPx = minorTickHeight.toPx()
                majorTickHeightPx = majorTickHeight.toPx()
                strokeWidthPx = tickWidth.toPx()
                markerHalfMaxWidthPx = stepPx / 2f
                textMaxLeftBorder = rulerSize.width - markerHalfMaxWidthPx
                textYOffset = rulerSize.height - majorTickHeightPx - 3.dp.toPx() - textStyle.lineHeight.toPx()
            }
        }

        fun draw() = with(drawScope) {
            drawBackground()
            drawTicksWithMarkers()
            drawOutline()
        }

        private fun ContentDrawScope.drawBackground() = drawRect(color = backgroundColor, size = rulerSize)

        private fun ContentDrawScope.drawOutline() = drawLine(
            outlineColor,
            start = Offset(0f, rulerSize.height - 1),
            end = Offset(rulerSize.width, rulerSize.height - 1),
            strokeWidth = outlineStrokeWidth.toPx(),
        )

        private fun ContentDrawScope.drawTicksWithMarkers() {
            getTicksPositions(
                step = step,
                screenSize = rulerSize.width,
                displayMetrics = displayMetrics,
                zeroOffset = zeroOffset.toCommonZeroOffset(),
                HORIZONTAL,
            ).forEach { (position, type, markerValue) ->
                drawTick(position, type)
                if (type == MAJOR) {
                    drawMarker(position, markerValue)
                }
            }
        }

        private fun DrawScope.drawTick(position: Float, type: TickType) {
            val height = when (type) {
                MINOR -> minorTickHeightPx
                MAJOR -> majorTickHeightPx
            }
            drawLine(
                color = ticksColor,
                start = Offset(x = position, y = rulerSize.height - height),
                end = Offset(x = position, y = rulerSize.height),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round,
            )
        }

        private fun DrawScope.drawMarker(position: Float, value: Float) {
            var horizontalPosition: Float = position - markerHalfMaxWidthPx
            val maxWidth: Float
            val markerTextStyle: TextStyle

            when {
                horizontalPosition <= 0 -> {
                    markerTextStyle = textStyleLeftMarker
                    horizontalPosition = 0f
                    maxWidth = markerHalfMaxWidthPx
                }

                horizontalPosition >= textMaxLeftBorder -> {
                    markerTextStyle = textStyleRightMarker
                    horizontalPosition = textMaxLeftBorder
                    maxWidth = markerHalfMaxWidthPx
                }

                else -> {
                    markerTextStyle = textStyleCenterMarker
                    maxWidth = (markerHalfMaxWidthPx * 2f)
                }
            }

            drawText(
                textMeasurer = textMeasurer,
                text = AnnotatedString(markerTextFormatter.format(value)),
                topLeft = Offset(
                    x = horizontalPosition,
                    y = textYOffset,
                ),
                style = markerTextStyle,
                maxLines = 1,
                size = Size(
                    width = maxWidth,
                    height = this.size.height,
                ),
            )
        }
    }
}
