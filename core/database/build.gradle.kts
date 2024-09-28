plugins {
    alias(libs.plugins.convention.dataModulePlugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}
kotlin {

    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
            }
        }

        val commonTest by getting {
            dependencies{
                implementation(libs.bundles.test)
            }


        }

    }


}
android {
    namespace = "database"
}
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
}