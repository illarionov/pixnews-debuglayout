/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.rowcolumn

import androidx.compose.ui.unit.Density

val oneToOneDensity: Density = object : Density {
    override val density: Float = 1f
    override val fontScale: Float = 1f
}
