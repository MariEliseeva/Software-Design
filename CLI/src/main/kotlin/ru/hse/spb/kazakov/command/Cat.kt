package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Environment
import java.io.File
import java.io.IOException

/**
 * Representation of cat command.
 */
class Cat(
    private val arguments: List<String>,
    private val environment: Environment
) : PipeCommand(environment.getLastCommand()) {
    override fun execute(): ExecutionResult {
        if (arguments.isEmpty()) {
            return ExecutionResult(getInput())
        }

        val errors = mutableListOf<String>()
        val output = arguments.joinToString(separator = "\n") {
            try {
                if (File(it).isAbsolute) {
                    File(it).readText()
                } else {
                    File(environment.getCurrentDir() + File.separator + it).readText()
                }
            } catch (exception: IOException) {
                errors.add("cat: $environment.getCurrentDir()$it: No such file")
                ""
            }
        }

        return ExecutionResult(output, errors)
    }
}