// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
//    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.kotlinKsp) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.daggerHilt) apply false

}
true // Needed to make the Suppress annotation work for the plugins block


