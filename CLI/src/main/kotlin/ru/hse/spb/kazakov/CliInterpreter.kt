package ru.hse.spb.kazakov

import ru.hse.spb.kazakov.command.*
import ru.hse.spb.kazakov.parser.*

/**
 * A class for interpreting [ParsingResult] produced by [ru.hse.spb.kazakov.parser.CliParser].
 */
class CliInterpreter {
    private val environment: Environment = Environment()
    /**
     * Returns interpreted from [parsingResult] chain of commands or null if there are no commands to interpret.
     */
    fun interpret(parsingResult: ParsingResult): PipeCommand? {
        environment.reset()
        parsingResult.assignments.forEach { makeAssignment(it) }
        parsingResult.pipeline.forEach { interpretCommand(it) }
        return environment.getLastCommand()
    }

    private fun makeAssignment(assignment: UnexpandedAssignment) {
        environment.setVariableValue(assignment.variable, expandString(assignment.value))
    }

    private fun interpretCommand(command: UnexpandedCommand) {
        val commandName = expandString(command.commandName)
        val commandArguments = command.arguments.map { expandString(it) }
        environment.setLastCommand(buildCommand(commandName, commandArguments))
    }

    private fun expandString(string: UnexpandedString): String =
        string.parts.joinToString(separator = "") {
            when (it.type) {
                Type.EXPANSION -> environment.getVariableValue(it.value)
                else -> it.value
            }
        }

    private fun buildCommand(name: String, arguments: List<String>) =
        when (name) {
            "cat" -> Cat(arguments, environment)
            "echo" -> Echo(arguments, environment)
            "wc" -> WC(arguments, environment)
            "pwd" -> Pwd(environment)
            "exit" -> Exit(environment)
            "cd" -> Cd(arguments, environment)
            "ls" -> Ls(arguments, environment)
            else -> UserCommand(name, arguments, environment)
        }
}


