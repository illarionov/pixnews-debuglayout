/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.gradle.multiplatform

import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.kotlin.dsl.withType

/*
 * Convention plugin that configures unit tests in projects with the Kotlin Multiplatform plugin
 */
tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "2G"
    testLogging {
        events = mutableSetOf(
            FAILED,
        )
    }
}
