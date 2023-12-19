@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {

    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
//    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.serialization)
}

android {
    namespace = "ru.agalkingps.mealapp.repo"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
/*    kapt {
        correctErrorTypes = true
        useBuildCache = true
    }
*/
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    // Room
    implementation(libs.bundles.room)
//    kapt(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.gson)
    // Serialization
    implementation(libs.serialization.json)
    // Libs
    implementation(project(":data"))
    // Hilt
    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)
    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.coroutines.test)
}