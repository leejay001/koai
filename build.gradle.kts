import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.0"
    application
    `maven-publish`
}

group = "com.lee"
version = "1.0.0"

apply(from = "deploy.gradle")

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.ktor:ktor-server-content-negotiation:3.1.0")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.0")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.0")

    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.squareup.okio:okio:3.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    implementation("io.ktor:ktor-client-core:3.1.0")
    implementation("io.ktor:ktor-client-auth:3.1.0")
    implementation ("io.ktor:ktor-client-cio:3.1.0")
    implementation ("io.ktor:ktor-client-json:3.1.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.0")
    implementation("io.ktor:ktor-serialization-jackson:3.1.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.0")

    implementation ("io.ktor:ktor-client-logging:3.1.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.majorVersion
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=all-compatibility"
}

sourceSets {
    val main by getting {
        java.srcDir("src/main/kotlin")
        kotlin.srcDir("src/main/kotlin")
    }
}
