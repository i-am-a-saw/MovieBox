import com.android.build.api.variant.BuildConfigField
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.iamasaw.moviebox"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.iamasaw.moviebox"
        minSdk = 24
        targetSdk = 37
        compileSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = project.rootProject.file("scratch.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val tmdb_api_key = properties.getProperty("TMDB_API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = tmdb_api_key
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.17.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("io.coil-kt:coil-compose:2.0.0-rc01")

    implementation("androidx.compose.ui:ui-text-google-fonts:1.12.0")


    implementation(libs.androidx.compose.runtime.livedata)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    val composeNavVersion = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$composeNavVersion")

    val koinVersion = "3.5.6"

    implementation("io.insert-koin:koin-core:$koinVersion")

    implementation("io.insert-koin:koin-android:$koinVersion")

    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}