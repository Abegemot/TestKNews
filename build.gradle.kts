import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("com.github.ben-manes.versions") version "0.42.0"
    id ("org.jetbrains.kotlin.jvm") version ("1.6.21")
    //  kotlin("plugin.serialization") version "1.5.30"
    //kotlin("plugin.serialization") version "1.5.21"
}

//group="com.begemot.ktestnews"
//version= "1.0-SNAPSHOT"

application{
    mainClass.set("com.begemot.ktestnews.TestKNewsKt")
}


/*repositories {
    mavenCentral()
    mavenLocal(){
        metadataSources {
            mavenPom()
            //artifact()
            ignoreGradleMetadataRedirection()
        }
    }
   // jcenter()
}*/

dependencies {
 //   implementation(platform("com.begemot.knewsplatform-bom:deps:0.0.1"))
    implementation(platform("com.begemota:sharedlibrary"))
    implementation( "org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-serialization-jvm")
    implementation("com.begemot:knewscommon")
    implementation("com.begemot:KNewsClient")
    //implementation( "ch.qos.logback:logback-classic")
    implementation("io.ktor:ktor-client-logging-jvm")
    implementation("io.github.microutils:kotlin-logging-jvm")
    implementation("org.jsoup:jsoup")
    implementation("com.begemot:KTransLib:1.0")
   // implementation("org.jetbrains.kotlin:kotlin-reflect")
}
/*
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

}*/