/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.ruler

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.tableOf
import ru.pixnews.debuglayout.DisplayMetrics
import ru.pixnews.debuglayout.ruler.RulerOrientation.HORIZONTAL
import ru.pixnews.debuglayout.ruler.RulerOrientation.VERTICAL
import ru.pixnews.debuglayout.ruler.Tick.TickType.MAJOR
import ru.pixnews.debuglayout.ruler.Tick.TickType.MINOR
import ru.pixnews.debuglayout.ruler.ZeroOffset.Alignment.CENTER
import ru.pixnews.debuglayout.ruler.ZeroOffset.Alignment.LEFT_TOP
import ru.pixnews.debuglayout.ruler.ZeroOffset.Alignment.RIGHT_BOTTOM
import kotlin.math.floor
import kotlin.test.Test

class TickTest {
    @Test
    fun getTicksPositions_should_return_correct_ticks_sequence_for_dp_steps() {
        val testCase = GetTickPositionsTestCase(
            step = 10.rulerDp,
            screenSize = 30f,
        )

        tableOf("Comment", "Test case", "Expected Ticks")
            .row(
                "General case",
                testCase,
                Tick(0f, MAJOR, 0f).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(10, 20, 30),
                ),
            )
            .row(
                "Offset from left",
                testCase.copy(zeroOffset = ZeroOffset(LEFT_TOP, 14.rulerDp)),
                Tick(position = 8f, markerValue = -10f).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(18, 28),
                ),
            )
            .row(
                "Offset from center",
                testCase.copy(zeroOffset = ZeroOffset(CENTER, 2.rulerDp)),
                Tick(position = 9f, type = MINOR, markerValue = -5f).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(19, 29),
                ),
            )
            .row(
                "Offset from right",
                testCase.copy(zeroOffset = ZeroOffset(RIGHT_BOTTOM, 5.rulerDp)),
                Tick(position = 0f, markerValue = -10f).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(10, 20, 30),
                ),
            )
            .row(
                "Huge offset",
                testCase.copy(zeroOffset = ZeroOffset(LEFT_TOP, (-5000).rulerDp)),
                Tick(position = 0f, markerValue = 5000f).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(10, 20, 30),
                ),
            ).forAll { _, test, expectedTicks ->
                val horizontalPositions = getTicksPositions(
                    step = test.step,
                    screenSize = test.screenSize,
                    displayMetrics = test.displayMetrics,
                    zeroOffset = test.zeroOffset,
                    orientation = HORIZONTAL,
                )
                val verticalPositions = getTicksPositions(
                    step = test.step,
                    screenSize = test.screenSize,
                    displayMetrics = test.displayMetrics,
                    zeroOffset = test.zeroOffset,
                    orientation = VERTICAL,
                )

                assertThat(horizontalPositions).containsExactlyInAnyOrder(elements = expectedTicks)
                assertThat(verticalPositions).containsExactlyInAnyOrder(elements = expectedTicks)
            }
    }

    @Test
    fun getTicksPositions_should_return_correct_sequences_for_millimeters() {
        val testDisplayMetrics = DisplayMetrics(
            density = 2f,
            xdpi = 2 * 25.4f,
            ydpi = 3 * 25.4f,
        )

        val horizontalPositions = getTicksPositions(
            step = 10.rulerMm,
            screenSize = 60f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset(CENTER, 2.rulerMm),
            orientation = HORIZONTAL,
        )

        val verticalPositions = getTicksPositions(
            step = 10.rulerMm,
            screenSize = 60f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset(CENTER, 1.rulerMm),
            orientation = VERTICAL,
        )

        assertThat(horizontalPositions.map { it.copy(position = floor(it.position)) })
            .containsExactlyInAnyOrder(
                elements = Tick(
                    position = 4f,
                    type = MINOR,
                    markerValue = -15f,
                ).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(14, 24, 34, 44, 54),
                ),
            )

        assertThat(verticalPositions.map { it.copy(position = floor(it.position)) })
            .containsExactlyInAnyOrder(
                elements = Tick(
                    position = 3f,
                    markerValue = -10f,
                ).withSubsequentTicks(
                    markerStep = 5,
                    positions = listOf(18, 33, 48),
                ),
            )
    }

    @Test
    fun getTicksPositions_should_return_correct_sequences_for_inches() {
        val horizontalPositions = getTicksPositions(
            step = 1.rulerInches,
            screenSize = 200f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset.ZERO,
            orientation = HORIZONTAL,
        )

        val verticalPositions = getTicksPositions(
            step = 1.rulerInches,
            screenSize = 200f,
            displayMetrics = testDisplayMetrics,
            zeroOffset = ZeroOffset.ZERO,
            orientation = VERTICAL,
        )

        assertThat(horizontalPositions.map { it.copy(position = floor(it.position)) })
            .containsExactlyInAnyOrder(
                elements = Tick(
                    position = 0f,
                    markerValue = 0f,
                ).withSubsequentTicks(
                    markerStep = 0.5f,
                    positions = listOf(55, 111, 166),
                ),
            )

        assertThat(verticalPositions.map { it.copy(position = floor(it.position)) })
            .containsExactlyInAnyOrder(
                elements = Tick(
                    position = 0f,
                    markerValue = 0f,
                ).withSubsequentTicks(
                    markerStep = 0.5f,
                    positions = listOf(61, 123, 184),
                ),
            )
    }

    internal data class GetTickPositionsTestCase(
        val step: DebugRulerMeasureUnit,
        val screenSize: Float = 200f,
        val displayMetrics: DisplayMetrics = testDisplayMetrics,
        val zeroOffset: ZeroOffset = ZeroOffset.ZERO,
        val orientation: RulerOrientation = HORIZONTAL,
    )

    companion object {
        private val testDisplayMetrics: DisplayMetrics = DisplayMetrics(
            density = 2f,
            xdpi = 111f,
            ydpi = 123f,
        )

        private fun Tick.withSubsequentTicks(markerStep: Int, positions: List<Int>): Array<Tick> =
            withSubsequentTicks(markerStep.toFloat(), positions)

        private fun Tick.withSubsequentTicks(
            markerStep: Float,
            positions: List<Int>,
        ): Array<Tick> = sequence<Tick> {
            var tickType = this@withSubsequentTicks.type
            var markerValue = this@withSubsequentTicks.markerValue
            (listOf(this@withSubsequentTicks.position) + positions).forEach { position ->
                yield(Tick(position.toFloat(), tickType, markerValue))
                tickType = if (tickType == MINOR) MAJOR else MINOR
                markerValue += markerStep
            }
        }.toList().toTypedArray()
    }
}
