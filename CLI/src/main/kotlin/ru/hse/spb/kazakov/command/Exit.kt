package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Environment
import kotlin.system.exitProcess

/**
 * Representation of exit command.
 */
class Exit(environment: Environment) : PipeCommand(environment.getLastCommand()) {
    override fun execute(): Nothing {
        exitProcess(0)
    }
}