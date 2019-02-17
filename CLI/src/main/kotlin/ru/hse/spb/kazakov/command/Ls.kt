package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Directory
import java.io.File
import java.util.*

/**
 * Class representing ls command. Take 0 or 1 arguments, if 0 given prints current directory content, if 1 prints given
 * directory content.
 */
open class Ls(private val arguments: List<String>, previous: PipeCommand?, private val currentDir: Directory) : PipeCommand(previous) {
    override fun execute(): ExecutionResult {
        if (arguments.isEmpty()) {
            return ExecutionResult(File(currentDir.getName()).list().joinToString(separator = " "))
        } else if (File(arguments[0]).exists() && File(arguments[0]).isAbsolute) {
            return ExecutionResult(File(arguments[0]).list().joinToString(separator = " "))
        } else {
            val newDirectory = currentDir.getName() + File.separator + arguments[0]
            if (File(newDirectory).exists() && File(newDirectory).isDirectory) {
                return ExecutionResult(File(newDirectory).list().joinToString(separator = " "))
            }
            return ExecutionResult("", Collections.singletonList("ls: " + arguments[0] + ": No such directory"))
        }
    }

}
