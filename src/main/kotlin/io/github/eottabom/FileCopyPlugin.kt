package io.github.eottabom

import org.gradle.api.Plugin
import org.gradle.api.Project

class FileCopyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val copyExtension = project.extensions.create("copyFiles", CopyInfo::class.java)
        project.afterEvaluate {
            project.tasks.create("filecopy", FileCopyTask::class.java) {
                copyInfo = copyExtension
                fileInfos = copyExtension.fileInfos
            }
        }
    }
}
