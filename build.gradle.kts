plugins {
    id("com.android.application") version "8.13.1" apply false
    id("com.android.library") version "8.13.1" apply false
    // 🔧 aquí forzamos Kotlin 1.9.25 (compatible con Compose Compiler 1.5.15)
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
