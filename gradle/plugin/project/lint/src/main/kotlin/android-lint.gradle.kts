/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.gradle.lint

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.TestPlugin

/*
 * Convention plugin that configures Android Lint in projects with the Android Gradle plugin
 */
project.plugins.withType(AppPlugin::class.java) {
    extensions.configure(CommonExtension::class.java) {
        lint {
            configureCommonAndroidLint()
            checkDependencies = true
        }
    }
}

listOf(
    LibraryPlugin::class.java,
    DynamicFeaturePlugin::class.java,
    TestPlugin::class.java,
).forEach { agpLibraryPlugin ->
    plugins.withType(agpLibraryPlugin) {
        extensions.configure(CommonExtension::class.java) {
            lint {
                configureCommonAndroidLint()
            }
        }
    }
}
