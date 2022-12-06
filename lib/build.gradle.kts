//Environment variables initialized in gradle.properties file or by command line
val publishingGroupId: String by project //Exemple value: io.github.viniciusalvesmello
val publishingArtifactId: String by project //Exemple value: kotlinlibr@arysample
val publishingVersion: String by project //Exemple value: 1.0.0
val artifactoryUrl: String by project //Exemple value: https://viniciusalvesmello.jfrog.io/artifactory
val artifactoryRepoKey: String by project //Exemple value: kotlin-library
val artifactoryUserName: String by project //Exemple value: youremail@email.com
val artifactoryPassword: String by project //Exemple value: YourPassword#9911

buildscript {
    dependencies {
        //The build information is sent to Artifactory in json format -> https://github.com/jfrog/build-info/
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.29.3")
    }
}

//Plugins
plugins {
    //Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.21"

    //Java-library plugin for API and implementation separation.
    `java-library`

    //Maven Publish Plugin provides the ability to publish build artifacts to an Apache Maven repository -> https://docs.gradle.org/current/userguide/publishing_maven.html
    `maven-publish`

    //The Gradle Artifactory Plugin allows you to deploy your build artifacts and build information to Artifactory -> https://www.jfrog.com/confluence/display/JFROG/Gradle+Artifactory+Plugin
    id("com.jfrog.artifactory") version "4.29.3"

    //Dokka is a documentation engine for Kotlin -> https://github.com/Kotlin/dokka
    id("org.jetbrains.dokka") version "1.7.20"

    //Detekt is a static code analysis tool for the Kotlin -> https://github.com/detekt/detekt
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

//Repositories
repositories {
    mavenCentral()
}

//Dependencies
dependencies {
    //Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    //Kotlin JDK 8 standard library
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    //Kotlin JUnit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    //Detekt itself provides a wrapper over ktlint as the formatting rule -> https://github.com/detekt/detekt#adding-more-rule-sets
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")
}

//Gradle tasks setup
tasks {
    //Create Jar file with project source code
    val sourcesJar by creating(Jar::class) {
        group = "build"
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    //Create Jar file with tests project source code
    val testsJar by creating(Jar::class) {
        dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME)
        group = "build"
        archiveClassifier.set("tests")
        from(sourceSets["test"].output)
    }

    //Add Jar files in artifactory
    artifacts {
        add("archives", sourcesJar)
        add("archives", testsJar)
    }

    //Configure Dokka Html
    dokkaHtml.configure {
        //Dokka html output directory
        outputDirectory.set(file("$rootDir/documentation/html"))
    }
}

//Detekt setup
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$rootDir/detekt-config.yml")
    baseline = file("$rootDir/detekt-baseline.xml")
}

//Publication setup
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = publishingGroupId
            artifactId = publishingArtifactId
            version = publishingVersion

            from(components["java"])
            setArtifacts(configurations.archives.get().allArtifacts)
        }
    }
}

//Artifactory setup
artifactory {
    setContextUrl(artifactoryUrl)

    publish(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
        repository(delegateClosureOf<groovy.lang.GroovyObject> {
            setProperty("repoKey", artifactoryRepoKey)
            setProperty("username", artifactoryUserName)
            setProperty("password", artifactoryPassword)
        })

        defaults(delegateClosureOf<groovy.lang.GroovyObject> {
            invokeMethod("publications", publishing.publications.names.toTypedArray())
        })
    })
}