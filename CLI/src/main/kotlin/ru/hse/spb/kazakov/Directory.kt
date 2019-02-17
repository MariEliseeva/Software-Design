package ru.hse.spb.kazakov

import org.apache.commons.io.FilenameUtils
import java.io.File

class Directory(private var directoryName: String) {

    fun getName(): String {
        return directoryName
    }

    fun setName(newName: String) {
        directoryName = checkFileSeparator(FilenameUtils.normalize(newName))
    }

    private fun checkFileSeparator(dirName: String): String {
        if (dirName.endsWith(File.separator)){
            return dirName.substring(0, dirName.length - 1)
        }
        return dirName
    }
}