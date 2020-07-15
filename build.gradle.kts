plugins {
    id("com.github.ben-manes.versions") version "0.28.0"
    id ("org.jetbrains.kotlin.jvm") version ("1.3.72")
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
    implementation("io.ktor:ktor-client-json")
    //implementation("io.ktor:ktor-client-gson")
    implementation("io.ktor:ktor-client-serialization-jvm")
    implementation("com.begemot:knewscommon")
}

/*
compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

 */