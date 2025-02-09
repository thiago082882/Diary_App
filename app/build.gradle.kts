plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.services)
    id("io.realm.kotlin")
//    id 'kotlin-kapt'
}
android {
    namespace = "br.thiago.diaryapp"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = "br.thiago.diaryapp"
        minSdk = ProjectConfig.minSdk
        targetSdk =ProjectConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
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
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
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
    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Firebase
    implementation(libs.firebase.storage.ktx)

    // Room components
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // Mongo DB Realm
    implementation(libs.library.sync)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Desugar JDK
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":data:mongo"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:write"))

}