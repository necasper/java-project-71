plugins {
    id("com.github.ben-manes.versions") version "0.53.0"
    application
    checkstyle
    kotlin("kapt") version "2.3.20"
}

application {
    mainClass = "hexlet.code.App"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}