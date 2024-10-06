plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.hanzeel.iptvandroidstudio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hanzeel.iptvandroidstudio"
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
        jvmTarget = "1.8"
    }
}

dependencies {
    val media3_version = "1.4.1" // Usa la versión más reciente disponible
    implementation ("androidx.media3:media3-exoplayer:$media3_version")       // Para reproducir audio/video
    implementation ("androidx.media3:media3-exoplayer-hls:$media3_version")
    implementation ("androidx.media3:media3-ui:$media3_version")               // UI de ExoPlayer
    implementation ("androidx.media3:media3-session:$media3_version")          // Sesión de medios (para integración con notificaciones y controles externos)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}