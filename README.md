<h1 align=center>ScVis</h1>

<p align=center>
<a href="https://jitpack.io/#kiyotoko/scvis"><img src="https://jitpack.io/v/karl-zschiebsch/scvis.svg" alt="Release"></a>
<a href="https://github.com/karl-zschiebsch/scvis/actions/workflows/codeql.yml"><img src="https://github.com/karl-zschiebsch/scvis/actions/workflows/codeql.yml/badge.svg" alt="CodeQL"></a>
<a href="https://github.com/karl-zschiebsch/scvis/actions/workflows/dependency-review.yml"><img src="https://github.com/karl-zschiebsch/scvis/actions/workflows/dependency-review.yml/badge.svg" alt="Dependency Review"></a>
<a href="https://github.com/karl-zschiebsch/scvis/actions/workflows/maven.yml"><img src="https://github.com/karl-zschiebsch/scvis/actions/workflows/maven.yml/badge.svg" alt="Java CI with Maven"></a>
</p>

The Calculator Java Library is a simple utility for interpreting and evaluating mathematical expressions provided as input strings. This library allows you to parse and evaluate arithmetic expressions and provides a command-line interface for interactive use.

## Features

- Implementation for Vectors, Matrices, Points, and Probabilities
- Tokenizes and evaluates mathematical expressions

## Getting Started

### Installation

Check out [Jitpack](https://jitpack.io/#karl-zschiebsch/scvis) to get it for your Gradle or Maven project.

### Usage

Use it to parse expressions.
```java
import org.scvis.Calculator;
import org.scvis.ScVis;

// Interpret and evaluate mathematical expressions ...
ScVis.interpret("sqrt(144)-(2+(8-3)!)"); // Returns [22.0]

// ... or create and start the interactive mode for continuous input
Calculator calculator = new Calculator();
calculator.runAndServe();
```

Perform calculations with vectors, matrices, points, and probabilities.
```java
import org.scvis.math.Vector;

new Vector(new double[]{2, 4, 7}).add(new Vector(new double[]{8, 3, 2})); // Returns [10, 7, 9]

new Vector(new double[]{3, 4}).length(); // Returns 5.0
```