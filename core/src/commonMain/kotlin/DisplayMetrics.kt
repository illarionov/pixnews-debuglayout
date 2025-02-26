/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

internal data class DisplayMetrics(val density: Float, val xdpi: Float, val ydpi: Float) {
    public fun withDensity(density: Float): DisplayMetrics {
        return if (this.density != density) {
            this.copy(density = density)
        } else {
            this
        }
    }
}

@Composable
internal expect fun Modifier.getDisplayMetrics(): DisplayMetrics
