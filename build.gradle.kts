plugins {
    id("java")
    id("io.qameta.allure") version "2.9.6"
}

allure {
    report {
        version.set("2.17.3")
    }
    adapter {
        aspectjWeaver.set(true)
        aspectjVersion.set("1.9.7")
        frameworks {
            junit5 {
                adapterVersion.set("2.17.3")
            }
        }
    }
}

group = "io.github.dmitrymiyuzov"
version = "1.0"



dependencies {
    implementation("io.qameta.allure:allure-java-commons:2.17.3")
    implementation("org.assertj:assertj-core:3.24.2")


    /*
    Для CSV файлов
     */
    implementation("com.opencsv:opencsv:5.7.1")

    /*
    Для XLSX файлов
     */
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    /*
    API
     */
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")
    implementation("io.rest-assured:rest-assured:4.4.0")
    implementation("com.github.erosb:everit-json-schema:1.14.4")

    /*
    Для конфигов
     */
    implementation("org.aeonbits.owner:owner:1.0.12")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.junit.platform:junit-platform-launcher:1.9.2")

    /*
    Для запуска тестов.
     */
    runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")

}

fun Test.common() {
    systemProperty("allure.results.directory", "$rootDir/allure-results")
    systemProperties["file.encoding"] = "UTF-8"
    systemProperties["encoding"] = "UTF-8"
    outputs.upToDateWhen { false }
}

tasks.register<Test>("runTest") {
    useJUnitPlatform()
    common();
}