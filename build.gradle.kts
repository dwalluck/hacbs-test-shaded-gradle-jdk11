import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2" apply true
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.stuartwdouglas.hacbs-test.simple:simple-jdk8:1.2.4")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

tasks.getByName("assemble").dependsOn("shadowJar")

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("HACBS Test Shaded Gradle JDK11")
                description.set("Test data for HACBS JVM build service (Gradle version)")
                url.set("https://github.com/dwalluck/hacbs-test-shaded-gradle-jdk11")
                properties.set(mapOf(
                    "maven.compiler.source" to "11",
                    "maven.compiler.target" to "11"
                ))
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("dwalluck")
                        name.set("David Walluck")
                        email.set("dwalluck@redhat.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/dwalluck/hacbs-test-shaded-gradle-jdk11.git")
                    developerConnection.set("scm:git:ssh://github.com/dwalluck/hacbs-test-shaded-gradle-jdk11.git")
                    url.set("https://github.com/dwalluck/hacbs-test-shaded-gradle-jdk11/")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype()
    }
}
