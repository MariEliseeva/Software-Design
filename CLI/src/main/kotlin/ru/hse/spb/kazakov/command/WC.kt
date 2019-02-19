package ru.hse.spb.kazakov.command

import ru.hse.spb.kazakov.Directory
import java.io.File
import java.io.IOException

/**
 * Representation of wc command.
 */
class WC(
    private val arguments: List<String>,
    prev: PipeCommand?,
    private val currentDir: Directory
) : PipeCommand(prev) {
    override fun execute(): ExecutionResult {
        if (arguments.isEmpty()) {
            return ExecutionResult(wc(getInput()))
        }

        val errors = mutableListOf<String>()
        val output = arguments.mapNotNull {
            try {
                val content = File(currentDir.getName() + File.separator + it).readText()
                wc(content)
            } catch (exception: IOException) {
                errors.add("wc: " + currentDir.getName() + File.separator + "$it: No such file")
                null
            }
        }.joinToString(separator = "\n")

        return ExecutionResult(output, errors)
    }

    private fun wc(text: String): String {
        val linesNumber = text.lines().size
        val wordsNumber = text.trim()
            .split("\\s+".toRegex())
            .size
        val bytesNumber = text.toByteArray().size

        return "$linesNumber $wordsNumber $bytesNumber"
    }
}
