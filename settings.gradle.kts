pluginManagement {
    includeBuild("gradle/plugin/settings")
}

plugins {
    id("ru.pixnews.debuglayout.gradle.settings.root")
}

rootProject.name = "pixnews-debuglayout"
include("core")
