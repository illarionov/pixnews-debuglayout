/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.ruler

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.tableOf
import kotlin.test.Test

class MarkerTextFormatterTest {
    @Test
    fun markerTextFormatter_should_format_value_correctly() {
        val formatter = MarkerTextFormatter()
        tableOf("Value", "Expected string representation")
            .row(0f, "0")
            .row(-0.0f, "0")
            .row(0.1f, "0.1")
            .row(0.999f, "1")
            .row(-0.999f, "-1")
            .row(1f, "1")
            .row(-1f, "-1")
            .row(1.123f, "1.1")
            .row(1.678f, "1.7")
            .forAll { value, expected ->
                val result = formatter.format(value)
                assertThat(result).isEqualTo(expected)
            }
    }
}
