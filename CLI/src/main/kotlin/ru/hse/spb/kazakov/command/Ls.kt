package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Directory
import java.io.File
import java.util.*

/**
 * Class representing ls command. Take 0 or 1 arguments, if 0 given prints current directory content, if 1 prints given
 * directory content. If more then one arguments passed -- ignores others.
 */
class Ls(private val arguments: List<String>, previous: PipeCommand?, private val currentDir: Directory) : PipeCommand(previous) {
    override fun execute(): ExecutionResult {
        if (arguments.isEmpty()) {
            return getLsResult(currentDir.getName())
        } else if (Directory.isDir(arguments[0]) && File(arguments[0]).isAbsolute) {
            return getLsResult(arguments[0])
        } else {
            val newDirectory = currentDir.getName() + File.separator + arguments[0]
            if (Directory.isDir(newDirectory)) {
                return getLsResult(newDirectory)
            }
            return ExecutionResult("", Collections.singletonList("ls: " + arguments[0] + ": No such directory"))
        }
    }

    private fun getLsResult(dir: String): ExecutionResult {
        return ExecutionResult(File(dir).list().joinToString(separator = " "))
    }
}
