plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    id("com.github.kpodvolecky.common-conventions")
    // maven local publish
    id("maven-publish")
    // git versioning
    alias(libs.plugins.git.version)
    //publish plugin
    alias(libs.plugins.plugin.publish)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.lesscss)
}

// set plugin group
group = "com.github.kpodvolecky.less"

// automatic plugin versioning by git version
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

gradlePlugin {
    plugins {
        create(property("ID").toString()) {
            id = property("ID").toString()
            group = property("GROUP").toString()
            implementationClass = property("IMPLEMENTATION_CLASS").toString()
            displayName = property("DISPLAY_NAME").toString()
            description = property("DESCRIPTION").toString()
        }
    }
}

// Configuration Block for the Plugin Marker artifact on Plugin Central
pluginBundle {
    website = property("WEBSITE").toString()
    vcsUrl = property("VCS_URL").toString()
    description = property("DESCRIPTION").toString()
    tags = listOf("less", "compiler", "mt")
}

/*
tasks.create("setupPluginUploadFromEnvironment") {
    doLast {
        val key = System.getenv("GRADLE_PUBLISH_KEY")
        val secret = System.getenv("GRADLE_PUBLISH_SECRET")

        if (key == null || secret == null) {
            throw GradleException("gradlePublishKey and/or gradlePublishSecret are not defined environment variables")
        }

        System.setProperty("gradle.publish.key", key)
        System.setProperty("gradle.publish.secret", secret)
    }
}
*/

// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

// Add a task to run the functional tests
val functionalTest by tasks.registering(Test::class) {
    group = "verification"
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
    useJUnitPlatform()
}

gradlePlugin.testSourceSets(functionalTestSourceSet)

tasks.named<Task>("check") {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}

tasks.named<Test>("test") {
    // Use JUnit Jupiter for unit tests.
    useJUnitPlatform()

}
