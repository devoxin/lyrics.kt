import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.lavalink)
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation(projects.client) {
        exclude(group = "io.ktor", module = "ktor-client-okhttp")
    }
    implementation(libs.ktor.client.java)
    implementation(projects.lavaplayer) {
        isTransitive = false
    }
    compileOnly(libs.lavalink.server)
}

lavalinkPlugin {
    name = "lyrics"
    apiVersion = libs.versions.lavalink.api
    serverVersion = libs.versions.lavalink.server
    path = "dev.schlaubi.lyrics.lavalink"
}

mavenPublishing {
    configure(KotlinJvm(JavadocJar.None()))
}

tasks {
    jar {
        exclude("org/intellij/**", "org/jetbrains/**", "org/slf4j/**", "kotlin/**", "kotlinx/serialization/**")
        exclude("DebugProbesKt.bin")
    }
}

publishing {
    repositories {
        maven("https://maven.lavalink.dev/releases") {
            credentials {
                username = System.getenv("LAVALINK_MAVEN_USER")
                password = System.getenv("LAVALINK_MAVEN_PASSWORD")
            }
        }
    }
}
