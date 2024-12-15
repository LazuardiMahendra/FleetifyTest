plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.fleetifytest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fleetifytest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //ViewModel
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.common.java8)

    //Material UI
    implementation(libs.material)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    //Hilt
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.compiler)

    //retrofit, gson
    implementation(libs.retrofit)
//    implementation(libs.converter.name)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    implementation(libs.logging.interceptor)

    //RXJava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.adapter.rxjava3)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.reactivestreams)

    //Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

}