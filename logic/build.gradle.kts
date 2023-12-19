import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val kotlinVersion = "1.9.20"
val slf4j_version = "1.7.36"

plugins {
    kotlin("jvm")
}

//kotlin {
//    val kotlinVersion = "1.9.20"
//    val slf4j_version = "1.7.36"
//
////    tasks.test {
////        useJUnitPlatform()
////        testLogging {
////            events("passed", "skipped", "failed")
////        }
////    }
//
////    jvm {
////        jvmToolchain(11)
////        withJava()
////    }
//}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("org.slf4j:slf4j-api:${slf4j_version}")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

//    testImplementation("org.jetbrains.kotlin:kotlin-test:${kotlinVersion}")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.19")
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.19")
    testImplementation("org.assertj:assertj-core:3.10.0")
}

tasks.test {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}
