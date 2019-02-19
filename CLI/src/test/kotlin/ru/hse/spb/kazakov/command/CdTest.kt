package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import ru.hse.spb.kazakov.Directory
import java.io.File
import java.nio.file.Paths
import java.util.*

class CdTest {
    private val resourcesPath: String = Paths.get("").toAbsolutePath().toString() + File.separator + "src" +
            File.separator + "test" + File.separator + "resources"
    @Test
    fun executeTest1() {
        val dir = Directory(resourcesPath)
        Cd(Collections.singletonList("dir1"), null, dir).getOutput()
        assertEquals(resourcesPath + File.separator + "dir1", dir.getName())
    }

    @Test
    fun executeTest2() {
        val dir = Directory(resourcesPath)
        Cd(Collections.singletonList("dir1" + File.separator + "dir3"), null, dir).getOutput()
        assertEquals(resourcesPath + File.separator + "dir1" + File.separator + "dir3", dir.getName())
    }

    @Test
    fun executeTest3() {
        val dir = Directory(resourcesPath + File.separator + "dir2")
        Cd(Collections.singletonList(".." + File.separator + "dir1" + File.separator + "dir3" +
                File.separator + ".."), null, dir).getOutput()
        assertEquals(resourcesPath + File.separator + "dir1", dir.getName())
    }
}