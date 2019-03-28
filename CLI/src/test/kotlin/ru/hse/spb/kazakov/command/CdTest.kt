package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.hse.spb.kazakov.Environment
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class CdTest {
    private val environment = Environment()

    @Before
    fun init() {
        val resourcesPath: Path = Paths.get(Paths.get("").toAbsolutePath().toString() + File.separator +
                "src" + File.separator + "test" + File.separator + "resources")
        environment.setCurrentDir(resourcesPath)
    }

    @Test
    fun executeTest1() {
        val path = environment.getCurrentDir()

        Cd(Collections.singletonList("dir1"), environment).getOutput()
        assertEquals(path + File.separator + "dir1", environment.getCurrentDir())
    }

    @Test
    fun executeTest2() {
        val path = environment.getCurrentDir()
        Cd(Collections.singletonList("dir1" + File.separator + "dir3"), environment).getOutput()
        assertEquals(path + File.separator + "dir1" + File.separator + "dir3", environment.getCurrentDir())
    }

    @Test
    fun executeTest3() {
        val path = environment.getCurrentDir()
        environment.setCurrentDir(Paths.get(environment.getCurrentDir() + File.separator + "dir2"))
        Cd(Collections.singletonList(".." + File.separator + "dir1" + File.separator + "dir3" +
                File.separator + ".."), environment).getOutput()
        assertEquals(path + File.separator + "dir1", environment.getCurrentDir())
    }

    @Test
    fun executeTest4() {
        val error = Cd(Arrays.asList("a", "b"), environment).getErrors()
        assertEquals("cd: Too many arguments.", error[0])
    }
}