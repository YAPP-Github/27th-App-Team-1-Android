import org.gradle.api.JavaVersion

object Configuration {
    const val MIN_SDK = 28
    const val COMPILE_SDK = 36
    const val TARGET_SDK = 36

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"

    val JAVA_VERSION = JavaVersion.VERSION_17
    const val JVM_TARGET = "17"

    const val APPLICATION_ID = "com.yapp.ndgl"
}
