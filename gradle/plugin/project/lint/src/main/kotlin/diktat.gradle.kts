/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

package at.released.debuglayout.gradle.lint

/*
 * Convention plugin that configures Diktat
 */
plugins {
    id("com.saveourtool.diktat")
}

diktat {
    diktatConfigFile = configRootDir.file("diktat.yml").asFile
    inputs {
        include("**/*.kt")
        include("**/*.gradle.kts")
        excludeNonLintedDirectories()
    }
    reporters {
        plain()
        sarif()
    }
    debug = false
    githubActions = false
}

tasks.named("mergeDiktatReports").configure {
    enabled = false
}

tasks.matching { it.name.startsWith("diktat") }.configureEach {
    notCompatibleWithConfigurationCache("invocation of 'Task.project' at execution time is unsupported")
}
