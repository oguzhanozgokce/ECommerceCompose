// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.navigationSafeArgs) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) version "2.44" apply false
}