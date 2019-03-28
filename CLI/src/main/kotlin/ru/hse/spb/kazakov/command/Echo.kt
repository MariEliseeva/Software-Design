package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Environment

/**
 * Representation of echo command.
 */
class Echo(private val arguments: List<String>, environment: Environment) : PipeCommand(environment.getLastCommand()) {
    override fun execute(): ExecutionResult = ExecutionResult(arguments.joinToString(separator = " "))
}