package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Environment
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Class representing cd command. Take 0 or 1 arguments, if 0 given changes current directory to home directory,
 * if 1 changes current directory to the given one.
 */
class Cd(private val arguments: List<String>,private val environment: Environment) : PipeCommand(environment.getLastCommand()) {
    override fun execute(): ExecutionResult {
         if (arguments.isEmpty()) {
            environment.setCurrentDir(Paths.get(System.getProperty("user.home")))
         } else if (arguments.size > 1) {
             return ExecutionResult("", Collections.singletonList("cd: Too many arguments."))
         } else {
            var path: Path = Paths.get(arguments[0])
            if (Files.isDirectory(path) && path.isAbsolute) {
                environment.setCurrentDir(path)
            } else {
                path = Paths.get(environment.getCurrentDir() + File.separator + arguments[0])
                if (Files.isDirectory(path)) {
                    environment.setCurrentDir(path)
                } else {
                    return ExecutionResult("", Collections.singletonList("cd: " + arguments[0] + ": No such directory"))
                }
            }
        }
        return ExecutionResult("")
    }
}