pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "NDGL"
include(":app")
include(":navigation")
include(":core:base")
include(":core:ui")
include(":core:util")

include(":feature:home")
include(":feature:travel")
include(":feature:travel-helper")
include(":feature:splash")
include(":feature:content-recommendation")

include(":data:core")
include(":data:auth")
include(":data:travel")

include(":baselineprofile")
