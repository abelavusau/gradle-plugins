plugins {
    kotlin("jvm") version "1.5.20"
    id("java-gradle-plugin")
}

group = "com.abelavusau.build.plugins"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.3")
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("osgi2gradle") {
            id = "com.abelavusau.build.plugins.osgi2gradle"
            implementationClass = "com.abelavusau.build.plugins.osgi2gradle.Osgi2GradlePlugin"
            version = "1.0.0"
        }
    }
}
