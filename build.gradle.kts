// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.3.6" apply false
    id("com.diffplug.spotless") version "8.10.4"
}

spotless {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX

    kotlin {
        target("app/src/**/*.kt")

        ktfmt("0.65").googleStyle().configure {
            it.setMaxWidth(120)
            it.setBlockIndent(4)
            it.setContinuationIndent(4)
            it.setRemoveUnusedImports(true)
        }
    }

    kotlinGradle {
        target("*.gradle.kts", "app/**/*.gradle.kts")

        ktfmt("0.65").googleStyle().configure {
            it.setRemoveUnusedImports(true)
        }
    }
}
