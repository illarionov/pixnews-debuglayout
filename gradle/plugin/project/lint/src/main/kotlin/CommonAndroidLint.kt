/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.gradle.lint

import com.android.build.api.dsl.Lint

/**
 * Android Lint defaults
 */
internal fun Lint.configureCommonAndroidLint() {
    quiet = true
    ignoreWarnings = false
    htmlReport = true
    xmlReport = true
    sarifReport = true
    checkDependencies = false
    ignoreTestSources = true

    disable += "ObsoleteSdkInt"
    informational += "GradleDependency"
}
