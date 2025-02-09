plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.jetbrains.kotlin.compose)
    id("io.realm.kotlin")
}

android {
    namespace = "br.thiago.util"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // No longer needed from Kotlin 2.0
//    composeOptions {
//        kotlinCompilerExtensionVersion ProjectConfig.extensionVersion
//    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}


dependencies {
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.library.sync)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)

    implementation(project(":core:ui"))
}