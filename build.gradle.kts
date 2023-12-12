plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "dev.argraur.fuzzy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:4.5.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:4.1.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass = "dev.argraur.fuzzy.MainKt"
}
