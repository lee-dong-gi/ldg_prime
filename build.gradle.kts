import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	val kotlinVersion = "1.6.0"
	dependencies {
		classpath("gradle.plugin.com.ewerk.gradle.plugins:querydsl-plugin:1.0.10")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
	}
}

repositories {
	mavenCentral()
	maven("https://plugins.gradle.org/m2/")
}

plugins {
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
	kotlin("plugin.jpa") version "1.6.0"
	kotlin("kapt") version "1.7.10"
}

group = "com.ldg"
version = "0.0.1-SNAPSHOT"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-graphql")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
	implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("com.querydsl:querydsl-mongodb")
	implementation("org.mongodb:mongodb-driver-sync")
	implementation("org.mongodb:bson")
	implementation("org.mongodb:mongo-java-driver")
	kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

	compileOnly ("org.projectlombok:lombok")
	annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:3.0.5")
	annotationProcessor ("org.projectlombok:lombok")

	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("com.auth0:java-jwt:3.18.1")
	implementation("javax.xml.bind:jaxb-api:2.3.1")

	// https://mvnrepository.com/artifact/org.apache.directory.studio/org.apache.commons.codec
	implementation("org.apache.directory.studio:org.apache.commons.codec:1.8")

	//redis
	implementation ("org.springframework.boot:spring-boot-starter-data-redis")

	//maria db
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	// kafka
	implementation("org.springframework.kafka:spring-kafka") // spring-kafka
	//implementation("org.apache.kafka:kafka-streams:3.2.3") // kafka-streams
	//implementation("org.apache.kafka:kafka-clients:3.2.3") // kafka-clients

	// query dsl
	val querydslVersion = "5.0.0"
	implementation("com.querydsl:querydsl-jpa:$querydslVersion")
	kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
	annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	//runtimeOnly("com.h2database:h2")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group="junit", module="unit")
	}

	testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = JavaVersion.VERSION_17.toString()
	}
	java.sourceCompatibility = JavaVersion.VERSION_17
}

tasks.named("compileJava") {
	inputs.files(tasks.named("processResources"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}

allOpen {// 이거 안하면 entity class를 모두 직접 open 선언해야함
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}
