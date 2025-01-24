/*
 * Copyright (c) 2024-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

plugins {
    id("at.released.debuglayout.gradle.multiplatform.android")
    id("at.released.debuglayout.gradle.multiplatform.kotlin")
    id("at.released.debuglayout.gradle.multiplatform.publish")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
}

group = "at.released.debuglayout"
version = "0.2-SNAPSHOT"

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
