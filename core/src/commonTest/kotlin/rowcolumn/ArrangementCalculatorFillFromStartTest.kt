/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.rowcolumn

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.tableOf
import at.released.debuglayout.rowcolumn.ArrangementCalculator.PaintedSubrange
import kotlin.test.Test

class ArrangementCalculatorFillFromStartTest {
    private val fillFromStartTestCases = tableOf("Comment", "Test params", "Expected Ranges")
        .row(
            "Auto rows/columns, large screen, 4 rows/columns",
            FillFromStartTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                size = 2.dp,
                gutter = 4.dp,
                offset = 3.dp,
                screenSize = 21f,
            ),
            listOf(3f, 9f, 15f).map { PaintedSubrange(it, 2f) },
        )
        .row(
            "1 row/column all screen",
            FillFromStartTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                size = 1.dp,
                gutter = 1.dp,
                offset = 0.dp,
                screenSize = 1f,
            ),
            listOf(PaintedSubrange(0f, 1f)),
        )
        .row(
            "Limited rows/columns, general case",
            FillFromStartTestCase(
                rowsColumnsCount = 3.asRowColumnCount,
                size = 5.dp,
                gutter = 1.dp,
                offset = 0.dp,
                screenSize = 20f,
            ),
            listOf(0f, 6f, 12f).map { PaintedSubrange(it, 5f) },
        )
        .row(
            "Small item width",
            FillFromStartTestCase(
                rowsColumnsCount = RowsColumnsCount.Auto,
                size = 0.1.dp,
                gutter = 4.dp,
                offset = 1.dp,
                screenSize = 20f,
            ),
            listOf(),
        )
        .row(
            "Limited rows/columns, cut from right on row",
            FillFromStartTestCase(
                rowsColumnsCount = RowsColumnsCount(5),
                size = 10.dp,
                gutter = 4.dp,
                offset = 3.dp,
                screenSize = 40f,
            ),
            listOf(3f, 17f, 31f).map { PaintedSubrange(it, 10f) },
        )
        .row(
            "Limited columns, cut from right on gutter right border",
            FillFromStartTestCase(
                rowsColumnsCount = RowsColumnsCount(5),
                size = 10.dp,
                gutter = 4.dp,
                offset = 3.dp,
                screenSize = 45f,
            ),
            listOf(3f, 17f, 31f).map { PaintedSubrange(it, 10f) },
        )

    @Test
    fun fillFromStartCalculator_should_return_correct_arrangement_on_columns() {
        fillFromStartTestCases.forAll { _, fillFromStartTestCase, expectedRanges ->
            val arrangement = DebugColumnsArrangement.Left(
                width = fillFromStartTestCase.size,
                gutter = fillFromStartTestCase.gutter,
                offset = fillFromStartTestCase.offset,
            )
            val calculator = ArrangementCalculator(arrangement, fillFromStartTestCase.rowsColumnsCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(fillFromStartTestCase.screenSize)
                }
            }

            assertThat(ranges).containsExactlyInAnyOrder(elements = expectedRanges.toTypedArray())
        }
    }

    @Test
    fun fillFromStartCalculator_should_return_correct_arrangement_on_rows() {
        fillFromStartTestCases.forAll { _, test, expectedRanges ->
            val arrangement = DebugRowsArrangement.Top(
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

    private data class FillFromStartTestCase(
        val rowsColumnsCount: RowsColumnsCount,
        val size: Dp,
        val offset: Dp,
        val gutter: Dp,
        val screenSize: Float,
    )
}
