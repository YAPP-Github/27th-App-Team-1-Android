plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
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
        register("ndglAndroidlibrary") {
            id = "ndgl.android.library"
            implementationClass = "NDGLAndroidlibraryPlugin"
        }
        register("ndglKotlinLibrary") {
            id = "ndgl.kotlin.library"
            implementationClass = "NDGLKotlinLibraryPlugin"
        }
    }
}
