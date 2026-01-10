plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("ndglApplication") {
            id = "ndgl.application"
            implementationClass = "NDGLApplicationPlugin"
        }
        register("ndglData") {
            id = "ndgl.data"
            implementationClass = "NDGLDataPlugin"
        }
        register("ndglFeature") {
            id = "ndgl.feature"
            implementationClass = "NDGLFeaturePlugin"
        }
        register("ndglAndroidLibrary") {
            id = "ndgl.android.library"
            implementationClass = "NDGLAndroidLibraryPlugin"
        }
        register("ndglKotlinLibrary") {
            id = "ndgl.kotlin.library"
            implementationClass = "NDGLKotlinLibraryPlugin"
        }
    }
}
