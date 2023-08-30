import org.gradle.jvm.tasks.Jar

plugins {
    id("java")

}


group = "com.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}





dependencies {

    implementation ("io.helidon.applications:helidon-mp:3.2.2")
    testImplementation ("io.helidon.microprofile.bundles:internal-test-libs:2.0.0-M3")
    testImplementation ("io.helidon.microprofile.cdi:helidon-microprofile-cdi:3.2.2")
    compileOnly ("io.helidon.microprofile.metrics:helidon-microprofile-metrics:3.2.2")
    implementation ("org.eclipse.microprofile:microprofile:6.0")
    implementation ("io.helidon.microprofile.health:helidon-microprofile-health:3.2.2")
    implementation ("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    implementation ("org.hibernate:hibernate-core:6.2.7.Final" )
    implementation ("io.helidon.common:helidon-common:3.2.2")
    implementation ("org.postgresql:postgresql:42.6.0")
    implementation ("io.helidon.microprofile.openapi:helidon-microprofile-openapi:3.2.2")



}

tasks.test {
    useJUnitPlatform()
}


/*
tasks.jar {
    manifest.attributes["Main-Class"] = "io.helidon.microprofile.cdi.Main"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.helidon.microprofile.cdi.Main"
    }
}
*/



val fatJar = task("fatJar", type = Jar::class) {
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "io.helidon.microprofile.cdi.Main"
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks.jar.get() as CopySpec)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}


