/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout

import androidx.compose.ui.graphics.drawscope.ContentDrawScope

internal fun interface DebugLayer {
    fun ContentDrawScope.draw()
}
