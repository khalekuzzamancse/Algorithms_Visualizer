plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    alias(libs.plugins.jetbrainsCompose)
 //   alias(libs.plugins.realm)
}

android {
    namespace = "com.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.khalekuzzaman.just.cse.algorithms_simulator"
        minSdk = 27
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    }
    kotlinOptions {
        jvmTarget = "1.8"

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(compose.ui)
    implementation(compose.material3)
    implementation(compose.preview)
    implementation(compose.materialIconsExtended)
    implementation(libs.kotlinx.coroutines.android)
    implementation(project(":feature:navigation"))

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0-alpha03")
//
    implementation("androidx.core:core-splashscreen:1.0.1")
}