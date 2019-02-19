package ru.hse.spb.kazakov

import org.apache.commons.io.FilenameUtils
import java.io.File

/**
 * Class which can store and normalize directory name.
 */
class Directory(private var directoryName: String) {
    init {
        normalizeName()
    }

    fun getName(): String {
        return directoryName
    }

    fun setName(newName: String) {
        directoryName = newName
        normalizeName()
    }

    private fun normalizeName(){
        directoryName = stopInRoot(removeFileSeparator(directoryName))
        if (directoryName != getRoot()) {
            directoryName = removeFileSeparator(FilenameUtils.normalize(directoryName))
        }
    }

    private fun removeFileSeparator(dirName: String): String {
        if (dirName.endsWith(File.separator) && dirName != getRoot()){
            return dirName.substring(0, dirName.length - 1)
        }
        return dirName
    }

    /**
     * If we go to the parent folder from the root we should stop (/../.. == /)
     */
    private fun stopInRoot(dirName: String): String {
        if (dirName.split(File.separator).stream().filter { t -> t == ".."}.toArray().size >
            dirName.split(File.separator).stream().filter { t -> t != ".."}.toArray().size - 1) {
            return getRoot()
        }
        return dirName
    }

    /**
     * Returns root directory name.
     */
    companion object {
        fun getRoot(): String {
            val operationSystem = (System.getProperty("os.name")).toUpperCase()
            if (operationSystem.contains("WIN")) {
                return System.getenv("SystemDrive") + "\\"
            }
            return "/"
        }

        fun isDir(name: String): Boolean {
            return File(name).exists() && File(name).isDirectory
        }
    }
}