package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Environment

/**
 * Representation of pwd command.
 */
class Pwd(private val environment: Environment) : PipeCommand(environment.getLastCommand()) {
    override fun execute(): ExecutionResult = ExecutionResult(environment.getCurrentDir())
}