plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass.set("practice.coffeemachine.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    jvmArgs("-Dsun.java2d.opengl=false", "-Dawt.useSystemAAFontSettings=on")
}
