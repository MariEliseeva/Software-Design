package ru.hse.spb.kazakov.command

import org.openqa.selenium.os.ExecutableFinder
import ru.hse.spb.kazakov.Environment
import java.io.File

/**
 * Representation of a user command.
 */
class UserCommand(
    private val name: String,
    private val arguments: List<String>,
    private val environment: Environment
) : PipeCommand(environment.getLastCommand()) {

    override fun execute(): ExecutionResult =
        if (ExecutableFinder().find(name) != null) {
            val commandCall = name + ' ' + arguments.joinToString(separator = " ")
            val process = Runtime.getRuntime().exec(commandCall, null, File(environment.currentDir.toString()))

            process.outputStream.bufferedWriter().use { it.write(getInput()) }
            val output = process.inputStream.bufferedReader().use { it.readText() }
            val error = process.errorStream.bufferedReader().use { it.readText() }

            ExecutionResult(output, listOf(error))
        } else {
            ExecutionResult("", listOf("$name: command not found"))
        }
}