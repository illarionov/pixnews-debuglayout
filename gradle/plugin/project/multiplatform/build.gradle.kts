/*
 * Copyright (c) 2023-2025, Alexey Illarionov and the compose-debuglayout project contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 * SPDX-License-Identifier: Apache-2.0
 */

plugins {
    `kotlin-dsl`
}

group = "at.released.debuglayout.gradle"

dependencies {
    implementation(project(":lint"))
    implementation(libs.agp.plugin.api)
    implementation(libs.kotlin.plugin)
    implementation(libs.gradle.maven.publish.plugin)
    implementation(libs.dokka.plugin)
    runtimeOnly(libs.agp.plugin)
}
