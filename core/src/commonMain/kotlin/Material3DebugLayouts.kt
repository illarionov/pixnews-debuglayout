/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import at.released.debuglayout.LargeLayoutRegions.EXPANDED
import at.released.debuglayout.LargeLayoutRegions.OFF
import at.released.debuglayout.LargeLayoutRegions.ON
import at.released.debuglayout.rowcolumn.RowsColumnsCount
import at.released.debuglayout.rowcolumn.asRowColumnCount

/**
 * 4dp baseline grid to help align text
 */
public fun DebugLayout.rowsText4Px(offset: Dp = 0.dp): Unit = rowsFromTop(
    rows = RowsColumnsCount.Auto,
    offset = offset,
    height = 1.dp,
    gutter = 3.dp,
    color = DebugLayoutDefaults.colorGuidelines.copy(alpha = 0.05f),
)

/**
 * 56dp area at the top
 */
public fun DebugLayout.rowTopToolbarArea(height: Dp = 56.dp): Unit = rowsFromTop(
    rows = 1.asRowColumnCount,
    height = height,
    color = DebugLayoutDefaults.colorSecondary,
)

/**
 * 56dp area at the bottom
 */
public fun DebugLayout.rowBottomNavigationBarArea(height: Dp = 56.dp): Unit = rowsFromBottom(
    rows = 1.asRowColumnCount,
    height = height,
    color = DebugLayoutDefaults.colorTertiary,
)

/**
 * Material 3, 0-599dp screen, 4 columns layout grid with 16dp gutters and margins
 */
public fun DebugLayout.extraSmallScreen4Columns(drawLayoutRegions: Boolean = false) {
    columnsStretch(
        columns = 4,
        margin = 16.dp,
        gutter = 16.dp,
        color = DebugLayoutDefaults.colorPrimary,
    )
    if (drawLayoutRegions) {
        rowTopToolbarArea()
        rowBottomNavigationBarArea()
    }
}

/**
 * Material 3, Extra-small screen (0-599dp), 3 columns layout grid with 16dp margins and gutters
 */
public fun DebugLayout.extraSmallScreen3Columns(drawLayoutRegions: Boolean = false) {
    columnsStretch(
        columns = 3,
        margin = 16.dp,
        gutter = 16.dp,
        color = DebugLayoutDefaults.colorPrimary,
    )
    if (drawLayoutRegions) {
        rowTopToolbarArea()
        rowBottomNavigationBarArea()
    }
}

/**
 * Material 3, Small screen (600-904dp), 8 columns layout grid with 32dp margins and gutters
 */
public fun DebugLayout.smallScreen8Columns(drawLayoutRegions: Boolean = false) {
    if (!drawLayoutRegions) {
        columnsStretch(
            columns = 8,
            margin = 32.dp,
            gutter = 32.dp,
            color = DebugLayoutDefaults.colorPrimary,
        )
    } else {
        columnsFromRight(
            columns = 8.asRowColumnCount,
            columnWidth = 46.dp,
            offset = 32.dp,
            color = DebugLayoutDefaults.colorPrimary,
        )
        columnsFromLeft(
            columns = 1.asRowColumnCount,
            columnWidth = 72.dp,
            offset = 0.dp,
            color = DebugLayoutDefaults.colorTertiary,
        )
        rowTopToolbarArea()
    }
}

/**
 * Material 3, Medium screen (905-1239dp), 12 columns layout grid with 24dp margins and gutters
 */
public fun DebugLayout.mediumScreen12Columns(drawLayoutRegions: Boolean = false) {
    if (!drawLayoutRegions) {
        columnsStretch(
            columns = 12,
            margin = 24.dp,
            gutter = 24.dp,
            color = DebugLayoutDefaults.colorPrimary,
        )
    } else {
        columnsFromRight(
            columns = 12.asRowColumnCount,
            columnWidth = 52.dp,
            offset = 32.dp,
            color = DebugLayoutDefaults.colorPrimary,
        )
        columnsFromLeft(
            columns = 1.asRowColumnCount,
            columnWidth = 72.dp,
            offset = 0.dp,
            color = DebugLayoutDefaults.colorTertiary,
        )
        rowTopToolbarArea()
    }
}

/**
 * Material 3, Medium screen (1240-1439dp), 12 columns layout grid with 24dp gutters and large margins
 */
public fun DebugLayout.mediumLaptopScreen12Columns(drawLayoutRegions: Boolean = false) {
    columnsStretch(
        columns = 12,
        margin = 200.dp,
        gutter = 24.dp,
        color = DebugLayoutDefaults.colorPrimary,
    )

    if (drawLayoutRegions) {
        columnsFromLeft(
            columns = 1.asRowColumnCount,
            columnWidth = 72.dp,
            offset = 0.dp,
            color = DebugLayoutDefaults.colorTertiary,
        )
        rowTopToolbarArea()
    }
}

public enum class LargeLayoutRegions {
    OFF, ON, EXPANDED
}

/**
 * Material 3, Large screen (1440+dp), 12 columns layout grid with 24dp gutters
 */
public fun DebugLayout.largeScreen12Columns(layoutRegions: LargeLayoutRegions = LargeLayoutRegions.OFF) {
    columnsFromCenter(
        columns = 12.asRowColumnCount,
        columnWidth = 72.dp,
        gutter = 24.dp,
        color = DebugLayoutDefaults.colorPrimary,
    )
    when (layoutRegions) {
        OFF -> Unit
        ON -> {
            columnsFromLeft(
                columns = 1.asRowColumnCount,
                columnWidth = 72.dp,
                offset = 0.dp,
                color = DebugLayoutDefaults.colorTertiary,
            )
            rowTopToolbarArea()
        }

        EXPANDED -> {
            columnsFromLeft(
                columns = 1.asRowColumnCount,
                columnWidth = 256.dp,
                offset = 0.dp,
                color = DebugLayoutDefaults.colorTertiary,
            )
            rowTopToolbarArea()
        }
    }
}
