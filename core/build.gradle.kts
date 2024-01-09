/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

plugins {
    id("ru.pixnews.debuglayout.gradle.multiplatform.android")
    id("ru.pixnews.debuglayout.gradle.multiplatform.kotlin")
    id("ru.pixnews.debuglayout.gradle.multiplatform.publish")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
}

group = "ru.pixnews.debuglayout"
version = "0.1-SNAPSHOT"

android {
    namespace = "ru.pixnews.debuglayout"
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
            api(compose.foundation)
            api(compose.ui)
        }
        /* Test source sets */
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.assertk)
        }
    }
}
