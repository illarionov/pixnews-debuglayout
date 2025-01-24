/*
 * Copyright (c) 2024-2025, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.gradle.multiplatform

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion

/*
 * Convention plugin that configures Android target in projects with the Kotlin Multiplatform plugin
 */
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("ru.pixnews.debuglayout.gradle.multiplatform.kotlin")
    id("ru.pixnews.debuglayout.gradle.lint.android-lint")
    id("com.android.library")
}

kotlin {
    androidTarget {
        publishLibraryVariants = listOf("release")
    }
}

extensions.configure<LibraryExtension>("android") {
    compileSdk = versionCatalogs.named("libs").findVersion("androidCompileSdk").get().displayName.toInt()
    defaultConfig {
        minSdk = versionCatalogs.named("libs").findVersion("androidMinSdk").get().displayName.toInt()
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = false
    }
}
