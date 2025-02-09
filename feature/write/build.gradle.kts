plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.ksp)
    id("io.realm.kotlin")
//    id 'kotlin-kapt'
}


android {
    namespace = "br.thiago.write"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk =  ProjectConfig.minSdk

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
        compose =true
    }
    // We don't need this from Kotlin 2.0
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
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.library.sync)
    implementation(libs.clock)
    implementation(libs.calendar)
    implementation(libs.core)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage.ktx)

    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":data:mongo"))
}