import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.lduboscq.vimystry.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }
}

val accompanist = "0.24.10-beta"
val compose = "1.2.0-beta03"
val coroutinesVersion = "1.6.1"
val composeMaterial3Version = "1.0.0-alpha13"
val nav_version = "2.4.2"
val koin_version = "3.2.0"

dependencies {
    implementation(project(":shared"))
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("androidx.compose.ui:ui:$compose")
    implementation("androidx.compose.ui:ui-tooling:$compose")
    implementation("androidx.compose.foundation:foundation:$compose")
    implementation("androidx.compose.material:material:$compose")
    implementation("androidx.compose.material:material-icons-core:$compose")
    implementation("androidx.compose.material:material-icons-extended:$compose")
    implementation("androidx.compose.runtime:runtime-livedata:$compose")
    implementation("androidx.compose.runtime:runtime-rxjava2:$compose")
    implementation("androidx.activity:activity-compose:1.6.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-rc02")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")
    implementation("com.google.accompanist:accompanist-permissions:$accompanist")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-androidx-navigation:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_version")
    implementation("com.google.android.exoplayer:exoplayer:2.17.1")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose")

    testImplementation("androidx.test:core:1.4.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.3")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.insert-koin:koin-core:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit4:$koin_version")
}
