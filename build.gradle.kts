buildscript {
    extra.apply {
        set("compose_version" , "1.3.0-beta01")
    }


    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath ("com.google.gms:google-services:4.3.14")

        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.3.1" apply false
    id ("com.android.library") version "7.3.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
}


task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}