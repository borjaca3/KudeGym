pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        google()
        mavenCentral()
        //maven { url = uri("https://jitpack.io")}
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven{url=uri("https://jitpack.io")}
    }
}

rootProject.name = "Api26"
include(":app")
 