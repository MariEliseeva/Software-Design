package ru.hse.spb.kazakov

import org.junit.Test

import org.junit.Assert.*
import java.io.File

class DirectoryTest {

    @Test
    fun getNameTest() {
        val dir = Directory("dir" + File.separator + "dir" + File.separator + "..")
        assertEquals("dir", dir.getName())
    }

    @Test
    fun getNameTest2() {
        val dir = Directory(Directory.getRoot() + ".." + File.separator + "..")
        assertEquals(Directory.getRoot(), dir.getName())
    }

    @Test
    fun setNameTest() {
        val dir = Directory("name1")
        dir.setName("name2/")
        assertEquals("name2", dir.getName())
    }
}