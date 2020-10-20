plugins {
    application
    id("com.github.ben-manes.versions") version "0.33.0"
    id ("org.jetbrains.kotlin.jvm") version ("1.4.10")
}

group="org.example"
version= "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal(){
        metadataSources {
            mavenPom()
            //artifact()
            ignoreGradleMetadataRedirection()
        }
    }
    jcenter()
}

dependencies {
    implementation(platform("com.begemot.knewsplatform-bom:deps:0.0.1"))
    implementation( "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.ktor:ktor-client-okhttp")
    implementation("io.ktor:ktor-client-serialization-jvm")
    implementation("com.begemot:knewscommon")
    implementation("com.begemot:KNewsClient")
    implementation( "ch.qos.logback:logback-classic")
    implementation("io.ktor:ktor-client-logging-jvm")
    implementation("org.jsoup:jsoup")
    implementation("com.begemot:KTransLib:1.0")
   // implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

}