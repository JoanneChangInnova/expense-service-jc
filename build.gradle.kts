

plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}


noArg {
	annotation("com.joanne.expenseservice.annotation.NoArg")
	invokeInitializers = true
}

group = "com.joanne"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	//implementation("javax.mail:mail:1.4")
	//implementation("org.springframework:spring-context-support:5.3.13")
	//must use spring-boot-starter-mail otherwise unexpected errors
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.apache.camel.springboot:camel-jackson-starter:3.8.0")
	implementation("org.apache.camel.springboot:camel-activemq-starter:3.8.0")
	implementation("org.springframework.boot:spring-boot-starter-activemq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.data:spring-data-jpa:2.6.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("com.vladmihalcea:hibernate-types-52:2.14.0")
	implementation ("com.google.code.gson:gson:2.8.5")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
	implementation("au.com.console:kotlin-jpa-specification-dsl:2.0.0")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
	implementation("org.springframework:spring-jms:5.3.16")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(kotlin("test"))
	testImplementation("io.mockk:mockk:1.10.4")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
