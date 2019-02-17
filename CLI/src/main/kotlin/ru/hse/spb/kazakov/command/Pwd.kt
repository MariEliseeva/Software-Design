package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Directory

/**
 * Representation of pwd command.
 */
class Pwd(prev: PipeCommand?, private val directory: Directory) : PipeCommand(prev) {
    override fun execute(): ExecutionResult = ExecutionResult(directory.getName())
}