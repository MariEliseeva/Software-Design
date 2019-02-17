package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import ru.hse.spb.kazakov.Directory
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class LsTest {
    private val resourcesPath: String = Paths.get("").toAbsolutePath().toString() + File.separator + "src" +
    File.separator + "test" + File.separator + "resources"

    @Test
    fun executeTest1() {
        val result = LsPublicExecute(ArrayList(), null, Directory(resourcesPath)).executePublic()
        assertEquals(HashSet(Arrays.asList("dir1", "dir2")), HashSet(result.split("\\s+".toRegex())))
    }

    @Test
    fun executeTest2() {
        val result = LsPublicExecute(Collections.singletonList("blablabla"), null, Directory(resourcesPath)).executePublicErrors()
        assertEquals("ls: blablabla: No such directory", result[0])
    }

    @Test
    fun executeTest3() {
        val result = LsPublicExecute(Collections.singletonList("dir1"), null, Directory(resourcesPath)).executePublic()
        assertEquals(HashSet(Collections.singletonList("dir3")), HashSet(Collections.singletonList(result)))
    }

    inner class LsPublicExecute(arguments: List<String>, prev: PipeCommand?, currentDir: Directory): Ls(arguments, prev, currentDir) {
        fun executePublic() = execute().output
        fun executePublicErrors() = execute().errors
    }
}