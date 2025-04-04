package io.github.eottabom

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.regex.Pattern

abstract class FileCopyTask : DefaultTask() {

    @Input
    lateinit var copyInfo: CopyInfo

    @Input
    var fileInfos: List<FileInfo> = listOf()

    @TaskAction
    fun copyFiles() {
        val basePath = File(project.projectDir, copyInfo.basePath)
        if (!basePath.exists()) {
            logger.lifecycle("Skipping: Base path ${basePath.path} does not exist.")
            return
        }

        val filesToProcess = fileInfos.ifEmpty { copyInfo.fileInfos }

        filesToProcess.forEach { fileInfo ->
            logger.lifecycle("Processing fileInfo: $fileInfo")
            findFile(fileInfo, basePath)
        }
    }

    private fun findFile(fileInfo: FileInfo, file: File) {
        val regex = Pattern.compile(fileInfo.sourceRegex)
        file.listFiles()?.forEach { fil ->
            if (fil.isDirectory) {
                findFile(fileInfo, fil)
            } else {
                val matcher = regex.matcher(fil.name)
                if (matcher.matches()) {
                    logger.lifecycle("Match found: ${fil.path}")

                    val targetFileName = matcher.replaceAll(fileInfo.targetRegex)
                    val target = File(fil.parent, targetFileName)

                    logger.lifecycle("Copying file: ${fil.path} → ${target.path}")
                    try {
                        Files.copy(fil.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    } catch (e: Exception) {
                        logger.error("Error copying file: ${e.message}")
                    }
                }
            }
        }
    }
}
