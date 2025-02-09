plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.jetbrains.kotlin.compose)
    id("io.realm.kotlin")
}

android {
    namespace = "br.thiago.auth"
    compileSdk= ProjectConfig.compileSdk

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
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.onetapcompose)
    implementation(libs.messagebarcompose)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.library.sync)

    implementation(project(":core:ui"))
    implementation(project(":core:util"))
}