// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
    id("com.diffplug.spotless") version "8.10.4"
}

spotless {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX

    kotlin {
        target("app/src/**/*.kt")

        ktlint("1.8.0")
            .setEditorConfigPath(
                rootProject.file(".editorconfig").absolutePath,
            ).editorConfigOverride(
                mapOf(
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                ),
            )
    }

    kotlinGradle {
        target("*.gradle.kts", "app/**/*.gradle.kts")

        ktlint("1.8.0")
            .setEditorConfigPath(
                rootProject.file(".editorconfig").absolutePath,
            )
    }
}
