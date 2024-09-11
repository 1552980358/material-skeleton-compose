plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "me.ks.chan.material.skeleton.compose"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.findByName("release"))
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
            }
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.material.skeleton.compose)

    testImplementation(libs.bundles.test)
    debugImplementation(libs.bundles.debug)
    androidTestImplementation(libs.bundles.android.test)
}