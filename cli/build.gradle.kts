val kotlinVersion = "1.9.20"
val slf4j_version = "1.7.36"

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation(project(":logic"))

    implementation("org.slf4j:slf4j-api:${slf4j_version}")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

//    testImplementation("org.jetbrains.kotlin:kotlin-test:${kotlinVersion}")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")
    testImplementation("io.kotest:kotest-property:5.7.2")
    testImplementation("org.assertj:assertj-core:3.10.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
