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
import kotlin.test.Test

class ArrangementCalculatorFillFromCenterTest {
    private val fillFromCenterTestCases = tableOf("Comment", "Test params", "Expected Ranges")
        .row(
            "Auto columns, large screen, 4 columns",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                width = 2.dp,
                gutter = 4.dp,
                screenSize = 20f,
            ),
            listOf(0f, 6f, 12f, 18f).map { PaintedSubrange(it, 2f) },
        )
        .row(
            "Auto columns, gutter all screen",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                width = 1.dp,
                gutter = 1.dp,
                screenSize = 1f,
            ),
            listOf(),
        )
        .row(
            "Auto columns:2 columns cut on both sides",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                width = 2.dp,
                gutter = 1.dp,
                screenSize = 2f,
            ),
            listOf(-1.5f, 1.5f).map { PaintedSubrange(it, 2f) },
        )
        .row(
            "Auto columns, small item width",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                width = 0.1.dp,
                gutter = 4.dp,
                screenSize = 20f,
            ),
            listOf(),
        )
        .row(
            "1 column, general case",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount(1),
                width = 3.dp,
                gutter = 4.dp,
                screenSize = 20f,
            ),
            listOf(PaintedSubrange(8.5f, 3f)),
        )
        .row(
            "1 column, cut from both sides",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount(1),
                width = 10.dp,
                gutter = 4.dp,
                screenSize = 5f,
            ),
            listOf(PaintedSubrange(0f, 5f)),
        )
        .row(
            "1 column, equal to screen size",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount(1),
                width = 10.dp,
                gutter = 4.dp,
                screenSize = 10f,
            ),
            listOf(PaintedSubrange(0f, 10f)),
        )
        .row(
            "3 columns, general case",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount(3),
                width = 3.dp,
                gutter = 4.dp,
                screenSize = 27f,
            ),
            listOf(5f, 12f, 19f).map { PaintedSubrange(it, 3f) },
        )
        .row(
            "2 columns, general case",
            FillFromCenterTestCase(
                rowsColumnsCount = RowsColumnsCount(2),
                width = 3.dp,
                gutter = 4.dp,
                screenSize = 27f,
            ),
            listOf(8.5f, 15.5f).map { PaintedSubrange(it, 3f) },
        )

    @Test
    fun fillFromCenterCalculator_should_return_correct_arrangement_on_columns() {
        fillFromCenterTestCases.forAll { _, test, expectedRanges ->
            val arrangement = DebugColumnsArrangement.Center(
                width = test.width,
                gutter = test.gutter,
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
    fun fillFromCenterCalculator_should_return_correct_arrangement_on_rows() {
        fillFromCenterTestCases.forAll { _, test, expectedRanges ->
            val arrangement = DebugRowsArrangement.Center(
                height = test.width,
                gutter = test.gutter,
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

    private data class FillFromCenterTestCase(
        val rowsColumnsCount: RowsColumnsCount,
        val width: Dp,
        val gutter: Dp,
        val screenSize: Float,
    )
}
