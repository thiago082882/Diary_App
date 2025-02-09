pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url ="https://oss.sonatype.org/content/repositories/snapshots")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url ="https://oss.sonatype.org/content/repositories/snapshots")

    }
}

rootProject.name = "DiaryApp"
include(":app")
include(":core:ui")
include(":core:util")
include(":data:mongo")
include(":feature:auth")
include(":feature:home")
include(":feature:write")
