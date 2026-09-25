plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.example.miformacionctma"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.miformacionctma"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        flavorDimensions += "environment"

        productFlavors {

            create("dev") {
                dimension = "environment"

                buildConfigField(
                    "String",
                    "API_BASE_URL",
                    "\"http://192.168.1.6:8000/\""
                )
            }

            create("stage") {
                dimension = "environment"

                buildConfigField(
                    "String",
                    "API_BASE_URL",
                    "\"https://stage.example.com/\""
                )
            }

            create("prod") {
                dimension = "environment"

                buildConfigField(
                    "String",
                    "API_BASE_URL",
                    "\"https://api.example.com/\""
                )
            }
        }
        buildFeatures {
            compose = true
            buildConfig = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    // CORE
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.1")

    // COMPOSE
    implementation(
        platform(
            "androidx.compose:compose-bom:2024.09.00"
        )
    )

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // CORREGIDO: era android.compose.foundation
    implementation("androidx.compose.foundation:foundation")

    implementation("io.coil-kt:coil-compose:2.7.0")

    debugImplementation(
        "androidx.compose.ui:ui-tooling"
    )

    // NAVIGATION
    implementation(
        "androidx.navigation:navigation-compose:2.8.3"
    )

    // VIEWMODEL
    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-compose:2.8.7"
    )

    // ROOM
    implementation(
        "androidx.room:room-runtime:2.7.0"
    )

    implementation(
        "androidx.room:room-ktx:2.7.0"
    )

    ksp(
        "androidx.room:room-compiler:2.7.0"
    )

    // SQLITE
    implementation(
        "androidx.sqlite:sqlite:2.4.0"
    )

    // RETROFIT
    implementation(
        "com.squareup.retrofit2:retrofit:2.11.0"
    )

    implementation(
        "com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0"
    )

    // OKHTTP
    implementation(
        "com.squareup.okhttp3:okhttp:4.12.0"
    )

    implementation(
        "com.squareup.okhttp3:logging-interceptor:4.12.0"
    )

    // SERIALIZATION
    implementation(
        "org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3"
    )

    // DATASTORE
    implementation(
        "androidx.datastore:datastore-preferences:1.1.1"
    )

    // COROUTINES
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0"
    )

    // DESUGARING
    coreLibraryDesugaring(
        "com.android.tools:desugar_jdk_libs:2.1.2"
    )

    // TEST
    testImplementation(
        "junit:junit:4.13.2"
    )
}