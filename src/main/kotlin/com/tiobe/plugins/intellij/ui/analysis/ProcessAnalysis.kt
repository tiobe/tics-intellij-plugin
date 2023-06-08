package com.tiobe.plugins.intellij.ui.analysis

import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.tiobe.plugins.intellij.analysis.AnalysisListener
import com.tiobe.plugins.intellij.logger.logger
import com.tiobe.plugins.intellij.ui.tree.FileTreeModelBuilder
import com.tiobe.plugins.intellij.utilities.TicsXML
import com.tiobe.plugins.intellij.utilities.TicsXmlReader.getXmlPath
import com.tiobe.plugins.intellij.utilities.TicsXmlReader.readXml
import com.tiobe.plugins.intellij.utilities.Violation
import org.jetbrains.io.LocalFileFinder
import java.io.File
import java.nio.file.Paths

class ProcessAnalysis(project: Project, private val model: FileTreeModelBuilder) {
    init {
        project.messageBus.connect().subscribe(AnalysisListener.TOPIC, AnalysisListener {
            invokeLater { analyze(it) }
        })
    }

    private fun analyze(analyzedFiles: List<String>) {
        try {
            val path = getXmlPath()
            File(path).let {file ->
                file.readText().let {
                    val value = readXml<TicsXML>(it)

                    logger.info("${value.violations.size} violations found in file $path:")
                    logger.debug(it)
                    analyzedFiles.forEach { path ->
                        LocalFileFinder.findFile(path)?.let { file ->
                            model.upsertFileWithAnnotations(file, getViolationsForFile(file, value.violations))
                        }
                    }
                }
                // Delete the XML file after use
                file.delete().let {
                    if (it) logger.info("$path deleted.")
                    else logger.warn("Failed to delete $path.")
                }
            }
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }

    private fun getViolationsForFile(file: VirtualFile, violations: List<Violation>): List<Violation> {
        return violations.filter {
            // Compare the files normalized, as a difference in slashed can mess up the comparison.
            Paths.get(it.file).normalize() == file.toNioPath().normalize()
        }
    }
}