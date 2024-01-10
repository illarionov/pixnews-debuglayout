/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

import ru.pixnews.debuglayout.gradle.lint.configRootDir
import ru.pixnews.debuglayout.gradle.lint.excludeNonLintedDirectories

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
