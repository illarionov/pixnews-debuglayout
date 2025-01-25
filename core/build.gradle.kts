/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

import org.jetbrains.compose.web.tasks.UnpackSkikoWasmRuntimeTask

plugins {
    id("at.released.debuglayout.gradle.multiplatform.android")
    id("at.released.debuglayout.gradle.multiplatform.kotlin")
    id("at.released.debuglayout.gradle.multiplatform.publish")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
}

group = "at.released.debuglayout"
version = "0.2"

android {
    namespace = "at.released.debuglayout"
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

kotlin {
    androidTarget()
    jvm()
    js(IR) {
        browser()
    }

/*
 TODO
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    macosX64()
    macosArm64()
    mingwX64()
*/

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.text)
        }
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.ui)
        }
        /* Test source sets */
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.assertk)
        }
    }
}

tasks.withType<UnpackSkikoWasmRuntimeTask>().configureEach {
    // Do not pack Skiko runtime into published JS klib. Workaround https://youtrack.jetbrains.com/issue/CMP-7479
    enabled = false
}
