rootProject.name = "TestKNews"
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    includeBuild("H:/prg/KnewsPlatform2")
}

/*
plugins {
    id("com.gradle.enterprise").version("3.4.1")
}

gradleEnterprise {
    // configuration
}
*/
