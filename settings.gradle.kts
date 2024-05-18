pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://jitpack.io") }
        jcenter()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
    }
}

rootProject.name = "Shopping Cart Store"
include(":app")
 