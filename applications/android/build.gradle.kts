plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    alias(libs.plugins.jetbrainsCompose)
 //   alias(libs.plugins.realm)
}

android {
    namespace = "com.khalekuzzaman.just.cse.dsavisualier2024"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.khalekuzzaman.just.cse.dsavisualier2024"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
//



}