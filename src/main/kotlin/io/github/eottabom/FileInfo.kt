package io.github.eottabom

import org.gradle.api.tasks.Input

data class FileInfo(
    @Input var sourceRegex: String,
    @Input var targetRegex: String,
)
