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
        val ls = Ls(ArrayList(), null, Directory(resourcesPath))
        assertEquals(HashSet(Arrays.asList("dir1", "dir2")),
            HashSet(ls.getOutput().split("\\s+".toRegex())))
    }

    @Test
    fun executeTest2() {
        val ls = Ls(Collections.singletonList("blablabla"), null, Directory(resourcesPath))
        assertEquals("ls: blablabla: No such directory", ls.getErrors()[0])
    }

    @Test
    fun executeTest3() {
        val ls = Ls(Collections.singletonList("dir1"), null, Directory(resourcesPath))
        assertEquals(HashSet(Collections.singletonList("dir3")),
            HashSet(Collections.singletonList(ls.getOutput())))
    }
}