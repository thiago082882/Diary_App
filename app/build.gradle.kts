plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //id ("kotlin-kapt")
    id ("com.google.devtools.ksp")
    id ("dagger.hilt.android.plugin")
    id ("io.realm.kotlin")
   //id ("com.google.gms.google-services")

}

android {
    namespace = "br.thiago.diaryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.thiago.diaryapp"
        minSdk = 24
        targetSdk = 34
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

        // Compose Navigation
        implementation("androidx.navigation:navigation-compose:2.7.3")

        // Firebase
        implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
        implementation("com.google.firebase:firebase-storage-ktx:20.2.1")

        // Room components
        implementation("androidx.room:room-runtime:2.5.2")
        ksp("androidx.room:room-compiler:2.5.2")
        implementation("androidx.room:room-ktx:2.5.2")

        // Runtime Compose
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

        // Splash API
        implementation("androidx.core:core-splashscreen:1.0.1")

        // Mongo DB Realm
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
        implementation("io.realm.kotlin:library-sync:1.11.0")

        // Dagger Hilt
        implementation("com.google.dagger:hilt-android:2.48")
        ksp("com.google.dagger:hilt-compiler:2.48")
        implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

        // Coil
        implementation("io.coil-kt:coil-compose:2.3.0")

        // Pager - Accompanist [DEPRECATED]
        // implementation("com.google.accompanist:accompanist-pager:0.27.0")

        // Date-Time Picker
        implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")

        // CALENDAR
        implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")

        // CLOCK
        implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

        // Message Bar Compose
        implementation("com.github.stevdza-san:MessageBarCompose:1.0.5")

        // One-Tap Compose
        implementation("com.github.stevdza-san:OneTapCompose:1.0.7")

        // Desugar JDK
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")



}