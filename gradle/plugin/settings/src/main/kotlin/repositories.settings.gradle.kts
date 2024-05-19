/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.gradle.settings

/*
 * Settings convention plugin that configures repositories used in the application
 */
pluginManagement {
    repositories {
        googleFiltered()
        mavenCentral()
        gradlePluginPortal()
    }

    // Get our own convention plugins from 'gradle/plugin/project'
    listOf(
        "project" to "debuglayout-gradle-project-plugins",
    ).forEach { (path, gradleProjectsPluginName) ->
        if (File(rootDir, "gradle/plugin/$path").exists()) {
            includeBuild("gradle/plugin/$path") {
                name = gradleProjectsPluginName
            }
        }
        // If not the main build, 'project' is located next to the build
        if (File(rootDir, "../$path").exists()) {
            includeBuild("../$path") {
                name = gradleProjectsPluginName
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jetbrainsCompose()
        googleFiltered()
    }
}

fun RepositoryHandler.jetbrainsCompose() {
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenContent {
            includeGroupByRegex("""org\.jetbrains(?:\..+)?""")
        }
    }
}

fun RepositoryHandler.googleFiltered() {
    google {
        mavenContent {
            // https://maven.google.com/web/index.html
            includeGroupByRegex("""android\.arch\..*""")
            includeGroupByRegex("""androidx\..+""")
            includeGroupByRegex("""com\.android(?:\..+)?""")
            includeGroupByRegex("""com\.crashlytics\.sdk\.android\..*""")
            includeGroupByRegex("""com\.google\.ads\..*""")
            includeGroupByRegex("""com\.google\.android\..*""")
            listOf(
                "com.google.ambient.crossdevice",
                "com.google.androidbrowserhelper",
                "com.google.ar",
                "com.google.ar.sceneform",
                "com.google.ar.sceneform.ux",
                "com.google.assistant.appactions",
                "com.google.assistant.suggestion",
                "com.google.camerax.effects",
                "com.google.chromeos",
                "com.google.d2c",
                "com.google.fhir",
                "com.google.firebase",
                "com.google.firebase.appdistribution",
                "com.google.firebase.crashlytics",
                "com.google.firebase.firebase-perf",
                "com.google.firebase.testlab",
                "com.google.gms",
                "com.google.gms.google-services",
                "com.google.jacquard",
                "com.google.mediapipe",
                "com.google.mlkit",
                "com.google.net.cronet",
                "com.google.oboe",
                "com.google.prefab",
                "com.google.relay",
                "com.google.test.platform",
                "com.google.testing.platform",
                "io.fabric.sdk.android",
                "tools.base.build-system.debug",
                "zipflinger",
            ).map(::includeGroup)

            includeModuleByRegex(
                """org\.jetbrains\.kotlin""",
                """kotlin-ksp|kotlin-symbol-processing-api""",
            )
        }
    }
}
