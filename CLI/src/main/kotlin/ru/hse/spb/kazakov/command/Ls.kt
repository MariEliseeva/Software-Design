package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Environment
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Class representing ls command. Take 0 or 1 arguments, if 0 given prints current directory content, if 1 prints given
 * directory content. If more then one arguments passed -- ignores others.
 */
class Ls(private val arguments: List<String>, private val environment: Environment) : PipeCommand(environment.getLastCommand()) {
    override fun execute(): ExecutionResult {
        if (arguments.isEmpty()) {
            return getLsResult(environment.getCurrentDir())
        } else if (arguments.size > 1) {
            return ExecutionResult("", Collections.singletonList("ls: Too many arguments."))
        } else {
            var path = Paths.get(arguments[0])
            if (Files.isDirectory(path) && path.isAbsolute) {
                return getLsResult(arguments[0])
            } else {
                path = Paths.get(environment.getCurrentDir() + File.separator + arguments[0])
                if (Files.isDirectory(path)) {
                    return getLsResult(path.toString())
                }
                return ExecutionResult("", Collections.singletonList("ls: " + arguments[0] + ": No such directory"))
            }
        }
    }

    private fun getLsResult(dir: String): ExecutionResult {
        return ExecutionResult(File(dir).list().joinToString(separator = " "))
    }
}
