/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.browser.window

// TODO
@Composable
@Suppress("ModifierComposable")
internal actual fun Modifier.getDisplayMetrics(): DisplayMetrics = window.devicePixelRatio.let {
    val floatDensity = it.toFloat()
    DisplayMetrics(
        density = it.toFloat(),
        xdpi = floatDensity * 160f,
        ydpi = floatDensity * 160f,
    )
}
