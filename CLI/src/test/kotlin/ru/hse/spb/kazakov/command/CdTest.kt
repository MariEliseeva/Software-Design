package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.hse.spb.kazakov.Environment
import java.io.File
import java.nio.file.Paths
import java.util.*

class CdTest {
    private val environment = Environment()
    private var path: String = Paths.get("").toAbsolutePath().toString() + File.separator +
            "src" + File.separator + "test" + File.separator + "resources"

    @Before
    fun init() {
        environment.currentDir = Paths.get(path)
    }

    @Test
    fun executeTest1() {
        Cd(Collections.singletonList("dir1"), environment).getOutput()
        assertEquals(path + File.separator + "dir1", environment.currentDir.toString())
    }

    @Test
    fun executeTest2() {
        Cd(Collections.singletonList("dir1" + File.separator + "dir3"), environment).getOutput()
        assertEquals(path + File.separator + "dir1" + File.separator + "dir3", environment.currentDir.toString())
    }

    @Test
    fun executeTest3() {
        environment.currentDir = Paths.get(path + File.separator + "dir2")
        Cd(Collections.singletonList(".." + File.separator + "dir1" + File.separator + "dir3" +
                File.separator + ".."), environment).getOutput()
        assertEquals(path + File.separator + "dir1", environment.currentDir.toString())
    }

    @Test
    fun executeTest4() {
        val error = Cd(Arrays.asList("a", "b"), environment).getErrors()
        assertEquals("cd: Too many arguments.", error[0])
    }
}