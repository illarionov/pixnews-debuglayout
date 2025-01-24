pluginManagement {
    includeBuild("gradle/plugin/settings")
}

plugins {
    id("at.released.debuglayout.gradle.settings.root")
}

rootProject.name = "compose-debuglayout"
include("core")
