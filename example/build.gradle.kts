plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "me.ks.chan.material.skeleton.compose.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.ks.chan.material.skeleton.compose.example"
        minSdk = 21
        targetSdk = compileSdk
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":material-skeleton-compose"))

    val androidxComposeBom = platform(libs.androidx.compose.bom)
    implementation(androidxComposeBom)
    androidTestImplementation(androidxComposeBom)

    api(libs.bundles.app)
    testImplementation(libs.bundles.test)
    debugImplementation(libs.bundles.debug)
    androidTestImplementation(libs.bundles.android.test)
}