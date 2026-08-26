plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.venussystem.venusmobile"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.venussystem.venusmobile"
        minSdk = 33
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // A BoM define as versoes de todas as libs do Firebase: nao coloque
    // versao nas linhas de firebase-* abaixo, ela vem daqui.
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Login com Google. O GoogleSignInClient antigo esta descontinuado;
    // o caminho atual e o Credential Manager.
    implementation(libs.credentials)
    implementation(libs.credentials.play.services)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}