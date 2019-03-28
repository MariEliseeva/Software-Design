package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.hse.spb.kazakov.Environment
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashSet

class LsTest {
    private val environment = Environment()

    @Before
    fun init() {
        environment.currentDir = Paths.get(Paths.get("").toAbsolutePath().toString() + File.separator +
                "src" + File.separator + "test" + File.separator + "resources")
    }

    @Test
    fun executeTest1() {
        val ls = Ls(Collections.emptyList(), environment)
        assertEquals(HashSet(Arrays.asList("dir1", "dir2")),
            HashSet(ls.getOutput().split("\\s+".toRegex())))
    }

    @Test
    fun executeTest2() {
        val ls = Ls(Collections.singletonList("blablabla"), environment)
        assertEquals("ls: blablabla: No such directory", ls.getErrors()[0])
    }

    @Test
    fun executeTest3() {
        val ls = Ls(Collections.singletonList("dir1"), environment)
        assertEquals(Collections.singletonList("dir3"),
            Collections.singletonList(ls.getOutput()))
    }

    @Test
    fun executeTest4() {
        val error = Ls(Arrays.asList("a", "b"), environment).getErrors()
        assertEquals("ls: Too many arguments.", error[0])
    }
}