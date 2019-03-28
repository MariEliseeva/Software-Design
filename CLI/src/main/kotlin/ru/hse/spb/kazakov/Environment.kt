package ru.hse.spb.kazakov

import ru.hse.spb.kazakov.command.PipeCommand
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class Environment {
    private val scope = HashMap<String, String>().withDefault { "" }
    private var lastCommand: PipeCommand? = null
    var currentDir: Path = Paths.get("").toAbsolutePath()
        set(value) {
            field = value.normalize()
        }

    fun getLastCommand(): PipeCommand? {
        return lastCommand
    }

    fun setLastCommand(command: PipeCommand) {
        lastCommand = command
    }

    fun reset() {
        lastCommand = null
    }

    fun setVariableValue(variableName: String, variableValue: String) {
        scope[variableName] = variableValue
    }

    fun getVariableValue(variableName: String): CharSequence {
        return scope.getValue(variableName)
    }
}