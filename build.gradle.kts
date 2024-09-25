// Root Level
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.realm) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
}

true // Needed to make the Suppress annotation work for the plugins block