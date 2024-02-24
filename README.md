<h1 align=center>Vision-Lang</h1>

<p align=center>
    <a href="https://jitpack.io/#kiyotoko/scvis">
        <img src="https://jitpack.io/v/karl-zschiebsch/scvis.svg" alt="Release">
    </a>
    <a href="https://github.com/karl-zschiebsch/scvis/actions/workflows/codeql.yml">
        <img src="https://github.com/karl-zschiebsch/scvis/actions/workflows/codeql.yml/badge.svg" alt="CodeQL">
    </a>
    <a href="https://github.com/karl-zschiebsch/scvis/actions/workflows/dependency-review.yml">
        <img src="https://github.com/karl-zschiebsch/scvis/actions/workflows/dependency-review.yml/badge.svg" alt="Dependency Review">
    </a>
    <a href="https://github.com/karl-zschiebsch/scvis/actions/workflows/maven.yml">
        <img src="https://github.com/karl-zschiebsch/scvis/actions/workflows/maven.yml/badge.svg" alt="Java CI with Maven">
    </a>
</p>

Vision-Lang is a learning project for building compilers. It is functional programming language that runs in the JVM.

## Installation

Check out [Jitpack](https://jitpack.io/#karl-zschiebsch/scvis) to get it for your Gradle or Maven project.

## Usage

### Execute files

Use the `FileInterpreter` to parse expressions from a file.

For example, if you want to use the following vision code:

```rust
a = 3
b = 4
c = 5

print(a^2+b^2, c^2)
```

You can do this with the following java code:

```java
import org.scvis.interpreter.FileInterpreter;

import java.io.File;
import java.io.IOException;

// ...

try(FileInterpreter interpreter = new FileInterpreter(new File(
        getClass().getResource("basics.vis").getFile()))){
    interpreter.interpretAll();
} catch (IOException ex){
    // handle exception
}
```

This will print out the following to the default output stream:

```py
>>> 25.0 25.0
```

### Run interactive

You can use the `InteractiveInterpreter` to parse expressions from the terminal:

```java
import org.scvis.interpreter.InteractiveInterpreter;

// ...

try (InteractiveInterpreter interpreter = new InteractiveInterpreter()) {
    interpreter.interpretAll();
}
```

This will look like this:

```py
>>> a = 3
3
>>> b = 4
4
>>> print(a^2 + b^2)
25.0 
[25.0]
>>> exit
@exit
```