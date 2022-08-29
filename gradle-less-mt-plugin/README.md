[![Gradle Plugins Release](https://img.shields.io/github/release/kpodvolecky/gradle-less-mt-plugin.svg)](https://plugins.gradle.org/plugin/io.github.kpodvolecky.less.gradle-less-mt-plugin)

# Gradle 7.x LESS compiler Plugin!
This project aims to compile your .less files to css. It uses [Houbie's Java wrapper to less compiler](https://github.com/houbie/lesscss).

# Quick Start
Compile your .less files in a [Gradle](https://gradle.org) build is easy! Just add this to your *build.gradle* file:

```Kotlin
plugins {
    id("io.github.kpodvolecky.less.gradle-less-mt-plugin") version "0.0.8"
}
```

Then you can configure compilation task:

```Kotlin
// Declare your sources
tasks.getByName<com.github.kpodvolecky.less.LessCompileTask>("lessCompile") {
    from(fileTree("${projectDir}/src/main/webapp/media/less") {
        include("client*.less")
        include("*client.less")
        include("theme/**/theme.less")
    })
    into(layout.projectDirectory.dir("src/main/webapp/media"))
}
```

# Built-in Tasks and Options
### lessCompile
- from = FileTree of source files
- into = Destination directory to flat-store resulting CSS files

[//]: # (## Contributors)

[//]: # (This project is made possible due to the efforts of these fine people:)

