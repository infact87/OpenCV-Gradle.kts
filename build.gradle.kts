plugins {
    id("com.android.library")
    id("kotlin-android")
}

val openCVersionName = "4.8.8"
val openCVersionCode = ((4 * 100 + 8) * 100 + 0) * 10 + 0

println("OpenCV: " + openCVersionName + " " + project.buildscript.sourceFile.toString())

android {
    namespace = "org.opencv"
    compileSdk = 34

    defaultConfig {
        externalNativeBuild {
            cmake {
                arguments += listOf("-DANDROID_STL=c++_shared")
                targets += listOf("opencv_jni_shared")
            }
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
        aidl = true
        buildConfig = true
    }

    buildTypes {
        debug {
            packagingOptions.setDoNotStrip(setOf("**/*.so"))
        }
        release {
            packagingOptions.setDoNotStrip(setOf("**/*.so"))
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild.cmake.path(project.projectDir.toString() + "/libcxx_helper/CMakeLists.txt")

    sourceSets.getByName("main") {
        jniLibs.srcDir("native/libs")
        java.srcDir("java/src")
        aidl.srcDir("java/src")
        res.srcDir("java/res")
        manifest.srcFile("java/AndroidManifest.xml")
    }
}

dependencies {
}
