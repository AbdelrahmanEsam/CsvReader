plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.devtools.ksp") version "1.7.10-1.0.6"
    id ("kotlin-parcelize")
}

android {
    namespace  =  "com.apptikar.csvreadertask"
    compileSdk = 33

    defaultConfig {
        applicationId ="com.apptikar.csvreadertask"
        minSdk =24
        targetSdk =33
        versionCode =1
        versionName ="1.0"

        testInstrumentationRunner =  "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary  = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose =  true
    }
    composeOptions {
        kotlinCompilerExtensionVersion  = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.kotlin)
    implementation (libs.androidx.compose.ui)
    implementation (libs.androidx.compose.material)
    implementation (libs.androidx.compose.tooling)
    implementation (libs.androidx.lifecycle.runtime)
    implementation (libs.androidx.activity.compose)
    implementation("androidx.documentfile:documentfile:1.0.1")

    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.test.ext)
    androidTestImplementation (libs.androidx.test.espresso)
    androidTestImplementation (libs.androidx.compose.ui.test)
    debugImplementation (libs.androidx.compose.tooling)
    debugImplementation (libs.androidx.compose.ui.test.manifest)


    // hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    kapt (libs.androidx.hilt.compiler)
    implementation (libs.androidx.hilt.navigation)

    // navigationCompose
    implementation(libs.androidx.navigation.compose)


    // OpenCSV
    implementation ("com.opencsv:opencsv:5.5.2")
    implementation(kotlin("reflect"))

//    implementation ("com.github.SUPERCILEX.poi-android:poi:3.17") {
//        exclude(group = "org.codehaus.woodstox", module = "stax2-api")
//    }

    implementation (group =  "org.apache.poi", name= "poi-ooxml", version= "3.17")
    implementation (group = "org.apache.xmlbeans", name= "xmlbeans", version= "3.1.0")
    implementation ("javax.xml.stream:stax-api:1.0")
    implementation ("com.fasterxml:aalto-xml:1.2.2")
}