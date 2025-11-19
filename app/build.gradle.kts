plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.onesec"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.onesec"
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
    kotlinOptions {
        // THIS IS THE FIX:
        // We make the Kotlin JVM target match the Java target (1.8)
        jvmTarget = "1.8"
    }
}

dependencies {

    // FIX: Using a stable, specific version of appcompat
    // This provides a stable version of 'activity' (1.9.0) and fixes error 1.
    implementation("androidx.appcompat:appcompat:1.7.0")

    // We can leave this one as-is from your catalog
    implementation(libs.material)

    // REMOVED: implementation(libs.activity)
    // This was conflicting with appcompat. We don't need it.

    // We can leave this one as-is
    implementation(libs.constraintlayout)

    // FIX: Using a stable, specific version of core-ktx
    // This fixes errors 2, 3, 4, and 5.
    implementation("androidx.core:core-ktx:1.13.1")

    // Test dependencies are fine
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}