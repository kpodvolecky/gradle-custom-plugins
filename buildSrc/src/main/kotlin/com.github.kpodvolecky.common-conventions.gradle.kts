import org.gradle.jvm.toolchain.JvmImplementation
import org.gradle.jvm.toolchain.JvmVendorSpec

plugins {
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
}

testing {
    suites {
        named("test", JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}
