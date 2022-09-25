[![Gradle Plugins Release](https://img.shields.io/github/release/kpodvolecky/gradle-less-mt-plugin.svg)](https://plugins.gradle.org/plugin/io.github.kpodvolecky.less.gradle-less-mt-plugin)

# Gradle 7.x LESS compiler Plugin!
This project aims to compile your .less files to css. It uses [Houbie's Java wrapper to less compiler](https://github.com/houbie/lesscss).

# Quick Start
Compile your .less files in a [Gradle](https://gradle.org) build is easy! Just add this to your *build.gradle* file:

```Kotlin
plugins {
    id("io.github.kpodvolecky.less.gradle-less-mt-plugin") version "0.1.0"
}
```

Then you can configure compilation task:

```Kotlin
// Declare your sources using lessCompiler extension
lessCompiler {
  sourceTree = fileTree("${projectDir}/src/main/webapp/media/less") {
      include("client*.less")
      include("*client.less")
      include("theme/**/theme.less")
  }
  destinationDirectory = layout.buildDirectory.dir("resources/css")
}

// you can override extension with
tasks.getByName<com.github.kpodvolecky.less.LessCompileTask>("lessCompile") {
    sourceTree = fileTree("${projectDir}/src/main/webapp/media/less") {
        include("client*.less")
        include("*client.less")
        include("theme/**/theme.less")
    }
    destinationDirectory = layout.projectDirectory.dir("src/main/webapp/media")
    // or 
    into(layout.projectDirectory.dir(getTemporaryDir().getAbsolutePath()))
}
```

# Built-in Tasks and Options
### lessCompiler
This is default configuration extension
- sourceTree = source ConfigurableFileTree
- destinationDirectory = destination directory of compiled css files, subdirs structure preserved

### lessCompile
- from = FileTree of source files
- into = Destination directory, sets destinationDirectory attribute
- destinationDirectory = output directory to store resulting CSS files

[//]: # (## Contributors)

[//]: # (This project is made possible due to the efforts of these fine people:)

