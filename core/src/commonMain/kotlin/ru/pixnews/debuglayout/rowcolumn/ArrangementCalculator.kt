/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

@file:Suppress("FLOAT_IN_ACCURATE_CALCULATIONS", "WRONG_MULTIPLE_MODIFIERS_ORDER")

package ru.pixnews.debuglayout.rowcolumn

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.pixnews.debuglayout.rowcolumn.ArrangementCalculator.PaintedSubrange
import ru.pixnews.debuglayout.rowcolumn.DebugColumnsArrangement.Center
import ru.pixnews.debuglayout.rowcolumn.DebugColumnsArrangement.Left
import ru.pixnews.debuglayout.rowcolumn.DebugColumnsArrangement.Right
import ru.pixnews.debuglayout.rowcolumn.DebugColumnsArrangement.Stretch
import ru.pixnews.debuglayout.rowcolumn.DebugRowsArrangement.Bottom
import ru.pixnews.debuglayout.rowcolumn.DebugRowsArrangement.Top

internal fun interface ArrangementCalculator {
    fun Density.arrange(totalSize: Float): Sequence<PaintedSubrange>
    data class PaintedSubrange(val start: Float, val size: Float)

    companion object {
        operator fun invoke(
            arrangement: DebugColumnsArrangement,
            rowsColumnCount: RowsColumnsCount,
        ): ArrangementCalculator {
            return when (arrangement) {
                is Center -> FillFromCenterCalculator(
                    rowsColumnCount,
                    arrangement.width,
                    arrangement.gutter,
                )

                is Left -> FillFromStartCalculator(
                    rowsColumnCount,
                    arrangement.width,
                    arrangement.offset,
                    arrangement.gutter,
                )

                is Right -> FillFromEndCalculator(
                    rowsColumnCount,
                    arrangement.width,
                    arrangement.offset,
                    arrangement.gutter,
                )

                is Stretch -> StretchCalculator(
                    rowsColumnCount.value,
                    arrangement.gutter,
                    arrangement.margin,
                )
            }
        }

        operator fun invoke(
            placement: DebugRowsArrangement,
            rowsColumnCount: RowsColumnsCount,
        ): ArrangementCalculator {
            return when (placement) {
                is DebugRowsArrangement.Center -> FillFromCenterCalculator(
                    rowsColumnCount,
                    placement.height,
                    placement.gutter,
                )

                is Top -> FillFromStartCalculator(
                    rowsColumnCount,
                    placement.height,
                    placement.offset,
                    placement.gutter,
                )

                is Bottom -> FillFromEndCalculator(
                    rowsColumnCount,
                    placement.height,
                    placement.offset,
                    placement.gutter,
                )

                is DebugRowsArrangement.Stretch -> StretchCalculator(
                    rowsColumnCount.value,
                    placement.gutter,
                    placement.margin,
                )
            }
        }
    }
}

private class FillFromCenterCalculator(
    val count: RowsColumnsCount,
    val size: Dp,
    val gutter: Dp,
) : ArrangementCalculator {
    override fun Density.arrange(totalSize: Float): Sequence<PaintedSubrange> {
        val itemSizePx = size.toPx().coerceAtMost(totalSize)
        val gutterSizePx = gutter.toPx()
        return fillFromCenter(
            maxItemsCount = count.value,
            totalLength = totalSize,
            itemSize = itemSizePx,
            gutterSize = gutterSizePx,
        ).map {
            PaintedSubrange(it, itemSizePx)
        }
    }
}

private class FillFromStartCalculator(
    val count: RowsColumnsCount,
    val size: Dp,
    val offset: Dp,
    val gutter: Dp,
) : ArrangementCalculator {
    override fun Density.arrange(totalSize: Float): Sequence<PaintedSubrange> {
        val itemSizePx = size.toPx().coerceAtMost(totalSize)
        val itemsCount = count.value
        val gutterSizePx = gutter.toPx()

        @Suppress("ComplexCondition")
        if (totalSize < 1 || itemSizePx < 1 || gutterSizePx < 0 || itemsCount < 0) {
            return emptySequence()
        }

        return fillTowardsEnd(
            startPosition = offset.toPx(),
            totalLength = totalSize,
            itemWidth = itemSizePx,
            gutterWidth = gutterSizePx,
        ).let {
            if (itemsCount != 0) it.take(itemsCount) else it
        }.map {
            PaintedSubrange(it, itemSizePx)
        }
    }
}

private class FillFromEndCalculator(
    val count: RowsColumnsCount,
    val size: Dp,
    val offset: Dp,
    val gutter: Dp,
) : ArrangementCalculator {
    override fun Density.arrange(totalSize: Float): Sequence<PaintedSubrange> {
        val startPositionRightBorder = totalSize - offset.toPx()
        val itemSizePx = size.toPx().coerceAtMost(totalSize)
        val itemsCount = count.value
        val gutterSizePx = gutter.toPx()

        @Suppress("ComplexCondition")
        if (startPositionRightBorder <= 0f || totalSize < 1 || itemSizePx < 1 || gutterSizePx < 0 || itemsCount < 0) {
            return emptySequence()
        }

        return fillTowardsStart(
            rightBorder = startPositionRightBorder,
            itemSize = itemSizePx,
            gutterSize = gutterSizePx,
        ).let {
            if (itemsCount != 0) it.take(itemsCount) else it
        }.map {
            PaintedSubrange(it, itemSizePx)
        }
    }
}

private class StretchCalculator(
    val count: Int,
    val gutter: Dp = 16.dp,
    val margin: Dp = 16.dp,
) : ArrangementCalculator {
    override fun Density.arrange(totalSize: Float): Sequence<PaintedSubrange> {
        if (count < 1) {
            return emptySequence()
        }

        val marginSizePx = margin.toPx()
        val gutterSizePx = gutter.toPx()
        if (marginSizePx < 0 || gutterSizePx < 0) {
            return emptySequence()
        }

        val availableSizePx = totalSize - marginSizePx * 2f - (count - 1) * gutterSizePx
        val itemSize = availableSizePx / count

        return fillFromCenter(
            maxItemsCount = count,
            totalLength = totalSize,
            itemSize = itemSize,
            gutterSize = gutterSizePx,
        ).map {
            PaintedSubrange(it, itemSize)
        }
    }
}

private fun fillFromCenter(
    maxItemsCount: Int,
    totalLength: Float,
    itemSize: Float,
    gutterSize: Float,
): Sequence<Float> {
    @Suppress("ComplexCondition")
    if (totalLength < 1 || itemSize < 1 || gutterSize < 0 || maxItemsCount < 0) {
        return emptySequence()
    }

    return sequence {
        val leftEnd: Float
        val rightStart: Float
        val center = totalLength / 2f

        if (maxItemsCount % 2 == 0) {
            leftEnd = center - gutterSize / 2f
            rightStart = leftEnd + gutterSize
        } else {
            val centerColumnStart = center - itemSize / 2f
            yield(centerColumnStart)
            leftEnd = centerColumnStart - gutterSize
            rightStart = centerColumnStart + itemSize + gutterSize
        }

        yieldAll(
            fillTowardsStart(
                rightBorder = leftEnd,
                itemSize = itemSize,
                gutterSize = gutterSize,
            ).let {
                if (maxItemsCount != 0) it.take(maxItemsCount / 2) else it
            },
        )
        yieldAll(
            fillTowardsEnd(
                startPosition = rightStart,
                totalLength = totalLength,
                itemWidth = itemSize,
                gutterWidth = gutterSize,
            ).let {
                if (maxItemsCount != 0) it.take(maxItemsCount / 2) else it
            },
        )
    }
}

private fun fillTowardsStart(
    rightBorder: Float,
    itemSize: Float,
    gutterSize: Float,
): Sequence<Float> {
    val itemWithGutter = itemSize + gutterSize
    return sequence {
        var itemEnd = rightBorder
        while (itemEnd > 0f) {
            yield(itemEnd - itemSize)
            itemEnd -= itemWithGutter
        }
    }
}

private fun fillTowardsEnd(
    startPosition: Float,
    totalLength: Float,
    itemWidth: Float,
    gutterWidth: Float,
): Sequence<Float> {
    val itemWithGutter = itemWidth + gutterWidth
    return sequence {
        var itemStart = startPosition
        while (itemStart < totalLength) {
            yield(itemStart)
            itemStart += itemWithGutter
        }
    }
}
