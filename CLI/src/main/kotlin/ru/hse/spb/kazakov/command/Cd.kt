package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Directory
import java.io.File
import java.util.*

/**
 * Class representing cd command. Take 0 or 1 arguments, if 0 given changes current directory to home directory,
 * if 1 changes current directory to the given one.
 */
class Cd(private val arguments: List<String>, prev: PipeCommand?, private val currentDir: Directory) : PipeCommand(prev) {
    override fun execute(): ExecutionResult {
        if (arguments.isEmpty()) {
            currentDir.setName(System.getProperty("user.home"))
        } else if (Directory.isDir(arguments[0]) && File(arguments[0]).isAbsolute) {
            currentDir.setName(arguments[0])
        } else {
            val newDirectory: String = if (currentDir.getName() != Directory.getRoot()) {
                currentDir.getName() + File.separator + arguments[0]
            } else {
                currentDir.getName() + arguments[0]
            }
            if (Directory.isDir(newDirectory)) {
                currentDir.setName(newDirectory)
            } else {
                return ExecutionResult("", Collections.singletonList("cd: " + arguments[0] + ": No such directory"))
            }
        }
        return ExecutionResult("")
    }
}