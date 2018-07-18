object Versions {
    const val compileSdk = 27
    const val targetSdk = 21
    const val minSdk = 21
    const val support_lib = "27.1.1"
    const val constraint_layout = "1.1.0"
    const val junit = "4.12"
    const val androidTestRunner = "1.0.1"
    const val espressoCore = "3.0.1"
    const val kotlinStdlib7 = "1.2.51"
    const val libphonenumberAndroid = "8.9.9"
}

object Libs {
    const val supportAnnotations = "com.android.support:support-annotations:${Versions.support_lib}"
    const val supportAppcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    const val supportDesign = "com.android.support:design:${Versions.support_lib}"
    const val supportConstraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"

    const val kotlnStdlib7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinStdlib7}"

    const val libphonenumberAndroid = "io.michaelrocks:libphonenumber-android:${Versions.libphonenumberAndroid}"

    const val junit = "junit:junit:${Versions.junit}"
    const val androidTestRunner = "com.android.support.test:runner:${Versions.androidTestRunner}"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espressoCore}"
}

object Sdk {
    const val compile = Versions.compileSdk
    const val target = Versions.targetSdk
    const val min = Versions.minSdk
}
