/*
 * Copyright (c) 2024, the pixnews-debuglayout project authors and contributors.
 * Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.debuglayout.gradle.multiplatform

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform

/*
 * Convention plugin with publishing defaults
 */
plugins {
    id("org.jetbrains.kotlin.multiplatform") apply false
    id("com.vanniktech.maven.publish.base")
    id("org.jetbrains.dokka")
}

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

mavenPublishing {
    publishing {
        repositories {
            maven {
                name = "test"
                setUrl(project.rootProject.layout.buildDirectory.dir("localMaven"))
            }
            maven {
                name = "PixnewsS3"
                setUrl("s3://maven.pixnews.ru/")
                credentials(AwsCredentials::class) {
                    accessKey = providers.environmentVariable("YANDEX_S3_ACCESS_KEY_ID").getOrElse("")
                    secretKey = providers.environmentVariable("YANDEX_S3_SECRET_ACCESS_KEY").getOrElse("")
                }
            }
        }
    }

    signAllPublications()

    configure(
        KotlinMultiplatform(javadocJar = JavadocJar.Empty()),
    )

    pom {
        name.set(project.name)
        description.set("A set of tools for Compose to help you align objects: layouts, grids and rulers")
        url.set("https://github.com/illarionov/pixnews-debuglayout")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("illarionov")
                name.set("Alexey Illarionov")
                email.set("alexey@0xdc.ru")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/illarionov/pixnews-debuglayout.git")
            developerConnection.set("scm:git:ssh://github.com:illarionov/pixnews-debuglayout.git")
            url.set("https://github.com/illarionov/pixnews-debuglayout")
        }
    }
}
