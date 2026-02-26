# filecopy-gradle-plugin

A Gradle plugin that automates file copying using regex-based rules.

## Goal

Build pipelines often require duplicating or renaming files — such as creating backups of configuration files, generating environment-specific variants, or staging build artifacts. Doing this manually or with custom scripts is error-prone and hard to maintain.

`filecopy-gradle-plugin` provides a simple, declarative way to define regex-based file copy rules directly in your Gradle build script. The plugin recursively scans a base directory, matches files by a source regex, and copies them with a target filename derived from capture groups — all as a reusable Gradle task.

---

## Requirements

- Java 21+
- Gradle 8.x

---

## Usage

### Kotlin DSL (`build.gradle.kts`)

```kotlin
plugins {
    id("io.github.eottabom.filecopy-gradle-plugin") version "1.0.1"
}

copyFiles {
    basePath = "src/main/resources"
    rule("(.*)\\.properties", "$1.properties.bak")
}
```

### Groovy DSL (`build.gradle`)

```groovy
plugins {
    id 'io.github.eottabom.filecopy-gradle-plugin' version '1.0.1'
}

copyFiles {
    basePath = 'src/main/resources'
    rule('(.*)\\.properties', '$1.properties.bak')
}
```

### Run

```bash
./gradlew filecopy
```

---

## Configuration

| Property   | Type               | Default     | Description                                                          |
|------------|--------------------|-------------|----------------------------------------------------------------------|
| `basePath` | `String`           | `/src/main` | Root directory to search for files recursively                       |
| `rule()`   | `(String, String)` | -           | File copy rule using Java regex (supports `$1`, `$2` capture groups) |

- `sourceRegex` — Java regex matched against the file name
- `targetRegex` — replacement expression for the copied file name

---

## Examples

**Multiple rules:**

```kotlin
copyFiles {
    basePath = "src/main"
    rule("(.*)\\.properties", "$1.properties.bak")
    rule("(.*)\\.xml", "$1.xml.bak")
    rule("(.*)\\.yml", "$1.yml.bak")
}
```

Copied files are placed in the **same directory** as the original file.

---

## License

This project is licensed under the [MIT License](LICENSE).
