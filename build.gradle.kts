plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("de.mkammerer:argon2-jvm:2.12")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jetbrains:annotations:26.0.2-1")
    testRuntimeOnly("org.jetbrains:annotations:26.0.2-1")

}

tasks.test {
    useJUnitPlatform()
}