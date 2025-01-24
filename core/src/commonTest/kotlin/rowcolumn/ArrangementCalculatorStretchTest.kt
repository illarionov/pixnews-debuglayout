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
import assertk.assertions.isEmpty
import assertk.tableOf
import at.released.debuglayout.rowcolumn.ArrangementCalculator.PaintedSubrange
import at.released.debuglayout.rowcolumn.DebugColumnsArrangement.Stretch
import kotlin.test.Test

public class ArrangementCalculatorStretchTest {
    private val stretchTestCases = tableOf("Comment", "Test params", "Expected Ranges")
        .row(
            "1 column",
            StretchTestCase(
                rowsColumnsCount = 1,
                gutter = 8.dp,
                screenSize = 20f,
                margin = 4.dp,
            ),
            listOf(PaintedSubrange(4f, 12f)),
        )
        .row(
            "2 columns",
            StretchTestCase(
                rowsColumnsCount = 2,
                gutter = 3.dp,
                margin = 0.dp,
                screenSize = 10f,
            ),
            listOf(0f, 6.5f).map { PaintedSubrange(it, 3.5f) },
        )
        .row(
            "3 columns",
            StretchTestCase(
                rowsColumnsCount = 3,
                gutter = 3.dp,
                margin = 4.dp,
                screenSize = 20f,
            ),
            listOf(4f, 9f, 14f).map { PaintedSubrange(it, 2f) },
        )
        .row(
            "Huge gutter",
            StretchTestCase(
                rowsColumnsCount = 3,
                gutter = 6.dp,
                margin = 0.dp,
                screenSize = 13f,
            ),
            listOf(),
        )
        .row(
            "Huge Margin",
            StretchTestCase(
                rowsColumnsCount = 3,
                gutter = 1.dp,
                margin = 6.dp,
                screenSize = 13f,
            ),
            listOf(),
        )

    @Test
    fun stretchCalculator_should_return_correct_arrangement_on_columns() {
        stretchTestCases.forAll { _, test, expectedRanges ->
            val arrangement = Stretch(
                gutter = test.gutter,
                margin = test.margin,
            )
            val calculator = ArrangementCalculator(arrangement, test.rowsColumnsCount.asRowColumnCount)

            val ranges = with(oneToOneDensity) {
                with(calculator) {
                    arrange(test.screenSize)
                }
            }

            assertThat(ranges).containsExactlyInAnyOrder(elements = expectedRanges.toTypedArray())
        }
    }

    @Test
    fun stretchCalculator_should_return_empty_list_on_invalid_input_values() {
        tableOf(
            "rows",
            "margin",
            "gutter",
            "screenSize",
        )
            .row(0, 1.0f, 1.0f, 10.0f)
            .row(-1, 1.0f, 1.0f, 10.0f)
            .row(1, -1.0f, 1.0f, 10.0f)
            .row(1, 0f, -1.0f, 10.0f)
            .row(1, 0f, 0f, 0.99f)
            .row(1, 0f, 0f, -1.0f)
            .row(1, 0f, 0f, 0f)
            .forAll { rowsCount, margin, gutter, screenSize ->
                val arrangement = Stretch(
                    margin = margin.dp,
                    gutter = gutter.dp,
                )
                val calculator = ArrangementCalculator(arrangement, rowsCount.asRowColumnCount)

                val ranges = with(oneToOneDensity) {
                    with(calculator) {
                        arrange(screenSize)
                    }
                }

                assertThat(ranges).isEmpty()
            }
    }

    private data class StretchTestCase(
        val rowsColumnsCount: Int,
        val margin: Dp,
        val gutter: Dp,
        val screenSize: Float,
    )
}
