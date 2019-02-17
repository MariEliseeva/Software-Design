package ru.hse.spb.kazakov.command

import org.junit.Test

import org.junit.Assert.*
import ru.hse.spb.kazakov.Directory
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList

class CdTest {
    @Test
    fun executeTest1() {
        val currentDir = Paths.get("").toAbsolutePath().toString()
        val dir = Directory(currentDir)
        CdPublicExecute(Collections.singletonList("src"), null, dir).executePublic()
        assertEquals(currentDir + File.separator + "src", dir.getName())
    }

    @Test
    fun executeTest2() {
        val currentDir = Paths.get("").toAbsolutePath().toString()
        val dir = Directory(currentDir)
        CdPublicExecute(Collections.singletonList("src/.."), null, dir).executePublic()
        assertEquals(currentDir, dir.getName())
    }

    @Test
    fun executeTest3() {
        val currentDir = Paths.get("").toAbsolutePath().toString()
        val dir = Directory(currentDir)
        CdPublicExecute(ArrayList(), null, dir).executePublic()
        assertEquals(System.getProperty("user.home"), dir.getName())
    }

    inner class CdPublicExecute(arguments: List<String>, prev: PipeCommand?, currentDir: Directory): Cd(arguments, prev, currentDir) {
        fun executePublic() {
            execute()
        }
    }
}