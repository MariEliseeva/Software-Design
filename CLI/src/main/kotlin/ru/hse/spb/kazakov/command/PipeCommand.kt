package ru.hse.spb.kazakov.command

/**
 * Representation of a CLI command.
 */
abstract class PipeCommand(private val prev: PipeCommand?) {
    private val result: ExecutionResult by lazy { execute() }

    /**
     * Returns command output.
     */
    fun getOutput(): String = result.output.also { prev?.result }

    /**
     * Returns all errors in pipe occurred so far.
     */
    fun getErrors(): List<String> = (prev?.getErrors() ?: emptyList()) + result.errors

    protected abstract fun execute(): ExecutionResult

    protected fun getInput() = prev?.getOutput() ?: ""

    companion object {
        /**
         * Creates command by its [name], [arguments] and [previous] command in pipe.
         * If there is no previous commands, then [previous] must be equal to null.
         */
        fun buildCommand(name: String, arguments: List<String>, previous: PipeCommand?) =
            when (name) {
                "cat" -> Cat(arguments, previous)
                "echo" -> Echo(arguments, previous)
                "wc" -> WC(arguments, previous)
                "pwd" -> Pwd(previous)
                "exit" -> Exit(previous)
                else -> UserCommand(name, arguments, previous)
            }
    }

    protected data class ExecutionResult(val output: String, val errors: List<String> = emptyList())
}