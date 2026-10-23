plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.praveenkumar.mytv.core.player"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
}
