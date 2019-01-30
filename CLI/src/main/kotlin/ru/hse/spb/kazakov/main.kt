package ru.hse.spb.kazakov

import org.antlr.v4.runtime.CharStreams
import ru.hse.spb.kazakov.antlr.ThrowingExceptionListener
import java.lang.IllegalArgumentException

/**
 * Request-response cycle.x
 */
fun main() {
    val lexer = CliLexer(CharStreams.fromString(""))
    lexer.addErrorListener(ThrowingExceptionListener())
    val parser = CliParser()

    while (true) {
        print("> ")
        val userInput = readLine()
        lexer.inputStream = CharStreams.fromString(userInput + '\n')

        val commands = try {
            parser.parseCommands(lexer.allTokens) ?: continue
        } catch (exception: IllegalArgumentException) {
            println(exception.message)
            continue
        } catch (exception: ParsingException) {
            println(exception.message)
            continue
        }

        val output = commands.getOutput()
        if (output != "") {
            println(output)
        }
        commands.getErrors().forEach { println(it) }
    }
}