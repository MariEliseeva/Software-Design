package ru.hse.spb.kazakov

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Token
import ru.hse.spb.kazakov.CliLexer.*
import ru.hse.spb.kazakov.command.*

/**
 * A class for parsing tokens produced by CLI lexer.
 */
class CliParser {
    private var tokens: List<Token> = emptyList()
    private var currentPosition = 0
    private val currentToken
        get() = tokens[currentPosition]
    private val nextToken
        get() = tokens[currentPosition + 1]
    private val scope = HashMap<String, String>().withDefault { "" }
    private var lastCommand: PipeCommand? = null

    /**
     * Returns parsed from [tokens] chain of commands.
     */
    fun parseCommands(tokens: List<Token>): PipeCommand? {
        this.tokens = tokens
        reset()

        while (parseAssignment()) {
            skipWS()
        }
        skipWS()
        parseCommands()

        if (currentPosition != tokens.lastIndex) {
            throw ParsingException("Error:(${currentToken.charPositionInLine + 1}) unexpected token: ${currentToken.text}")
        }

        return lastCommand
    }

    private fun reset() {
        currentPosition = 0
        lastCommand = null
    }

    private fun skipWS() {
        if (currentToken.type == WS) {
            currentPosition++
        }
    }

    private fun parseAssignment(): Boolean {
        if (currentToken.type == IDENTIFIER && nextToken.type == ASSIGN) {
            val variable = currentToken.text
            currentPosition += 2
            val value = parseText()
            scope[variable] = value
            return true
        }

        return false
    }

    private fun parseText(): String {
        val stringBuilder = StringBuilder()

        while (currentToken.isTextPart()) {
            stringBuilder.append(
                when (currentToken.type) {
                    WQ -> parseWeakQuoting()
                    FQ -> parseFullQuoting()
                    else -> parseWord()
                }
            )
        }

        return stringBuilder.toString()
    }

    private fun Token.isTextPart(): Boolean =
        type == WQ || type == FQ || isWordPart()

    private fun parseWeakQuoting(): String =
        expandVariables(currentToken.text.drop(1).dropLast(1)).also { currentPosition++ }

    private fun parseFullQuoting(): String = currentToken.text.drop(1).dropLast(1).also { currentPosition++ }

    private fun parseWord(): String {
        val stringBuilder = StringBuilder()

        while (currentToken.isWordPart()) {
            stringBuilder.append(currentToken.text)
            currentPosition++
        }

        return expandVariables(stringBuilder.toString())
    }

    private fun Token.isWordPart(): Boolean =
        type == WORD || type == ASSIGN || type == PIPE || type == EXPANSION || type == IDENTIFIER

    private fun expandVariables(text: String): String {
        val expandingLexer = ExpandingLexer(CharStreams.fromString(text))
        return expandingLexer.allTokens.joinToString(separator = "") { token ->
            when (token.type) {
                ExpandingLexer.EXPANSION -> scope.getValue(token.text.drop(1))
                else -> token.text
            }
        }
    }

    private fun parseCommands() {
        parseCommand()
        while (currentToken.type == PIPE) {
            val pipeToken = currentToken
            currentPosition++
            skipWS()
            if (!parseCommand()) {
                throw ParsingException("Error:(${pipeToken.charPositionInLine + 1}) unexpected token: ${pipeToken.text}")
            }
            skipWS()
        }
    }

    private fun parseCommand(): Boolean {
        val name = parseCommandCallPart()
        if (name == "") {
            return false
        }

        val arguments = mutableListOf<String>()
        skipWS()
        var argument = parseCommandCallPart()
        while (argument != "") {
            arguments.add(argument)
            skipWS()
            argument = parseCommandCallPart()
        }

        lastCommand = buildCommand(name, arguments, lastCommand)
        return true
    }

    private fun parseCommandCallPart(): String {
        val stringBuilder = StringBuilder()

        while (currentToken.isTextPart() && currentToken.type != PIPE) {
            stringBuilder.append(
                when (currentToken.type) {
                    WQ -> parseWeakQuoting()
                    FQ -> parseFullQuoting()
                    else -> parseArgument()
                }
            )
        }

        return stringBuilder.toString()
    }

    private fun parseArgument(): String {
        val stringBuilder = StringBuilder()

        while (currentToken.isWordPart() && currentToken.type != PIPE) {
            stringBuilder.append(currentToken.text)
            currentPosition++
        }

        return expandVariables(stringBuilder.toString())
    }

    private fun buildCommand(name: String, arguments: List<String>, previous: PipeCommand?) =
        when (name) {
            "cat" -> Cat(arguments, previous)
            "echo" -> Echo(arguments, previous)
            "wc" -> WC(arguments, previous)
            "pwd" -> Pwd(previous)
            "exit" -> Exit(previous)
            else -> UserCommand(name, arguments, previous)
        }
}

