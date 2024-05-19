/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import java.awt.Toolkit

@Composable
@ReadOnlyComposable
@Suppress("ModifierComposable")
internal actual fun Modifier.getDisplayMetrics(): DisplayMetrics {
    val dpi = Toolkit.getDefaultToolkit().screenResolution
    return DisplayMetrics(
        density = dpi / 160f,
        xdpi = dpi.toFloat(),
        ydpi = dpi.toFloat(),
    )
}
