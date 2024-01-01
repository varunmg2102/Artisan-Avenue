pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        repositories {
            maven { url = uri("https://jitpack.io") }
        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        repositories {
            maven { url = uri("https://jitpack.io") }
        }
    }
}

rootProject.name = "Artisans Avenue"
include(":app")
 