package io.github.eottabom

import org.gradle.api.tasks.Input

open class CopyInfo {

    @Input
    var basePath: String = "/src/main"

    @Input
    var fileInfos: MutableList<FileInfo> = mutableListOf()

    fun rule(sourceRegex: String, targetRegex: String) {
        fileInfos.add(FileInfo(sourceRegex, targetRegex))
    }

}
