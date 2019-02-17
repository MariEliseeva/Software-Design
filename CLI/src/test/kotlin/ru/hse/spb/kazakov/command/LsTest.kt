package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import ru.hse.spb.kazakov.Directory
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashSet

class LsTest {

    @Test
    fun executeTest1() {
        val currentDir = Paths.get("").toAbsolutePath().toString()
        val result = LsPublicExecute(Collections.singletonList("src"), null, Directory(currentDir)).executePublic()
        assertEquals(HashSet(Arrays.asList("main", "test")), HashSet(result.split(" ")))
    }

    @Test
    fun executeTest2() {
        val currentDir = Paths.get("").toAbsolutePath().toString()
        val result = LsPublicExecute(Collections.singletonList("blablabla"), null, Directory(currentDir)).executePublicErrors()
        assertEquals("ls: blablabla: No such directory", result[0])
    }

    @Test
    fun executeTest3() {
        val currentDir = Paths.get("").toAbsolutePath().toString()
        val result = LsPublicExecute(Collections.singletonList("src" + File.separator + "main"), null, Directory(currentDir)).executePublic()
        assertEquals(HashSet(Arrays.asList("kotlin", "antlr")), HashSet(result.split("\\s+".toRegex())))
    }

    inner class LsPublicExecute(arguments: List<String>, prev: PipeCommand?, currentDir: Directory): Ls(arguments, prev, currentDir) {
        fun executePublic() = execute().output
        fun executePublicErrors() = execute().errors
    }
}