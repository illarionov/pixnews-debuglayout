/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.rowcolumn

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.tableOf
import ru.pixnews.debuglayout.rowcolumn.ArrangementCalculator.PaintedSubrange
import ru.pixnews.debuglayout.rowcolumn.DebugColumnsArrangement.Right
import ru.pixnews.debuglayout.rowcolumn.DebugRowsArrangement.Bottom
import kotlin.test.Test

class ArrangementCalculatorFillFromEndTest {
    private val fillFromEndTestCases = tableOf("Comment", "Test params", "Expected Ranges")
        .row(
            "Auto rows/columns, large screen, 4 rows/columns",
            FillFromEndTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                size = 2.dp,
                gutter = 4.dp,
                offset = 3.dp,
                screenSize = 21f,
            ),
            listOf(16f, 10f, 4f).map { PaintedSubrange(it, 2f) },
        )
        .row(
            "1 row/column all screen",
            FillFromEndTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                size = 1.dp,
                gutter = 1.dp,
                offset = 0.dp,
                screenSize = 1f,
            ),
            listOf(PaintedSubrange(0f, 1f)),
        )
        .row(
            "Limited columns, general case",
            FillFromEndTestCase(
                rowsColumnsCount = 3.asRowColumnCount,
                size = 5.dp,
                gutter = 1.dp,
                offset = 0.dp,
                screenSize = 20f,
            ),
            listOf(15f, 9f, 3f).map { PaintedSubrange(it, 5f) },
        )
        .row(
            "Small item width",
            FillFromEndTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                size = 0.1.dp,
                gutter = 4.dp,
                offset = 1.dp,
                screenSize = 20f,
            ),
            listOf(),
        )
        .row(
            "Limited columns, cut from left on row",
            FillFromEndTestCase(
                rowsColumnsCount = RowsColumnsCount(5),
                size = 10.dp,
                gutter = 4.dp,
                offset = 3.dp,
                screenSize = 40f,
            ),
            listOf(-1f, 13f, 27f).map { PaintedSubrange(it, 10f) },
        )

    @Test
    fun fillFromEndCalculator_should_return_correct_arrangement_on_columns() {
        fillFromEndTestCases.forAll { _, test, expectedRanges ->
            val arrangement = Right(
                width = test.size,
                gutter = test.gutter,
                offset = test.offset,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            assertThat(ranges).containsExactlyInAnyOrder(elements = expectedRanges.toTypedArray())
        }
    }

    @Test
    fun fillFromLeftCalculator_should_return_correct_arrangement_on_rows() {
        fillFromEndTestCases.forAll { _, test, expectedRanges ->
            val arrangement = Bottom(
                height = test.size,
                gutter = test.gutter,
                offset = test.offset,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            assertThat(ranges).containsExactlyInAnyOrder(elements = expectedRanges.toTypedArray())
        }
    }

    private data class FillFromEndTestCase(
        val rowsColumnsCount: RowsColumnsCount,
        val size: Dp,
        val offset: Dp,
        val gutter: Dp,
        val screenSize: Float,
    )
}
