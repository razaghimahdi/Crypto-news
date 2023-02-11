package dependencies

object Build {
     const val androidBuildTools = "com.android.tools.build:gradle:${Versions.gradle}"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    const val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_component}"
}