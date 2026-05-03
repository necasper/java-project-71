plugins {
    id("com.github.ben-manes.versions") version "0.53.0"
    application
    checkstyle
    jacoco
    id("org.sonarqube") version "7.2.0.6526"
    kotlin("kapt") version "2.3.20"
}

application {
    mainClass = "hexlet.code.App"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

plugins {
}

sonar {
    properties {

    }
}

sonar {
    properties {
        property("sonar.projectKey", "necasper_java-project-71")
        property("sonar.organization", "necasper")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}
