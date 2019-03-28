package ru.hse.spb.kazakov

import ru.hse.spb.kazakov.command.PipeCommand
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class Environment {
    private val scope = HashMap<String, String>().withDefault { "" }
    private var lastCommand: PipeCommand? = null
    private var currentDir: Path = Paths.get("").toAbsolutePath()

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

    fun getCurrentDir(): String {
        return currentDir.toString()
    }

    fun setCurrentDir(newDir: Path) {
        currentDir = newDir.normalize()
    }
}