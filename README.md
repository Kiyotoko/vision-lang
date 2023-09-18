# scvis

[![Release](https://jitpack.io/v/karl-zschiebsch/scvis.svg)](https://jitpack.io/#karl-zschiebsch/scvis)
[![CodeQL](https://github.com/karl-zschiebsch/scvis/actions/workflows/codeql.yml/badge.svg)](https://github.com/karl-zschiebsch/scvis/actions/workflows/codeql.yml)
[![Dependency Review](https://github.com/karl-zschiebsch/scvis/actions/workflows/dependency-review.yml/badge.svg)](https://github.com/karl-zschiebsch/scvis/actions/workflows/dependency-review.yml)
[![Java CI with Maven](https://github.com/karl-zschiebsch/scvis/actions/workflows/maven.yml/badge.svg)](https://github.com/karl-zschiebsch/scvis/actions/workflows/maven.yml)

scvis is a Java library that provides various features for game development, geometry operations, and protocol handling. It offers a set of tools and utilities to simplify common tasks in these domains.

## Table of Content

- [Table of Content](#table-of-content)
- [Installation](#installation)
	- [Maven](#maven)
	- [Gradle](#gradle)
- [Features](#features)
	- [Entity](#entity)
	- [Geometry](#geometry)
	- [Proto](#proto)
- [Contributing](#contributing)

## Installation

Note: Replace ``tag`` with the specific release or commit tag you want to use.

### Maven

To use scvis in your Maven project, you need to add the JitPack repository and the dependency to your pom.xml file.

Add the following repository to the ``<repositories>`` section of your pom.xml file:

```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

Then, add the scvis dependency to the ``<dependencies>`` section:

```xml
<dependency>
	<groupId>com.github.karl-zschiebsch</groupId>
	<artifactId>scvis</artifactId>
	<version>tag</version>
</dependency>
```

### Gradle

To use scvis in your Gradle project, you need to add the JitPack repository and the dependency to your build.gradle file.

Add the following repository to the repositories section of your ``build.gradle`` file:

```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

Then, add the scvis dependency to the ``dependencies`` section:

```gradle
dependencies {
	implementation 'com.github.karl-zschiebsch:scvis:tag'
}
```

## Features

### Entity

The Entity feature in scvis provides a collection of interfaces that are directly or indirectly related to entities and their updates over time. Entities represent objects or elements in a system or application, and this feature offers a set of interfaces to manage and update them effectively.

### Geometry

The Geometry feature offers a collection of classes and methods for performing geometric operations. It includes utilities for working with vectors, lines, polygons, and other geometric entities. This feature is useful for applications that require geometric calculations, such as collision detection and transformation.

### Proto

The Proto feature provides classes and utilities for handling protocol entries. It includes functionality for serialization and deserialization of protocol buffers, as well as other related operations. This feature is beneficial for applications that communicate using

## Contributing

scvis welcomes contributions from the community. If you would like to contribute, please follow these guidelines:

1. Fork the repository and clone it locally.
2. Create a new branch for your feature or bug fix.
3. Make your changes and ensure that the code compiles without any errors.
4. Commit your changes and push them to your forked repository and submit a pull request. Please provide a short explanation of your changes in the pull request.
5. We will review your changes and merge your request!

If you have any questions or need assistance during the contribution process, feel free to reach out to the project maintainers or refer to the project's documentation.

We appreciate your contribution and thank you for your support in making scvis even better!