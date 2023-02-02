package com.flipperplz.dayzidea.actions

import com.flipperplz.dayzidea.vfs.DayZ_PboFilesystem
import com.flipperplz.dayzidea.vfs.PboFilesystem
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFileFilter
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.ArchiveFileSystem
import java.io.File
import java.nio.file.Path

class DayZ_PboTestAction : AnAction("pbotest") {

    private val systemPboPath = "Users/global_projects/kotlin/TestProjects/Scripts.pbo"
    private val randomPboPath = "DayZEditorLoader/Scripts/4_World/DayZEditorLoader/Entities/ObjectRemover.c"
    override fun actionPerformed(e: AnActionEvent) {
        val test = VirtualFileManager.getInstance().findFileByNioPath(Path.of("C:/$systemPboPath"))
        val instance = PboFilesystem.instance
        VfsUtilCore.iterateChildrenRecursively(test!!, VirtualFileFilter.ALL) {
            println(it.fileType)
            println(it.fileSystem)
            it.isDirectory
        }
        println(test!!.fileType);
        println(test.fileSystem);
        println()
    }
}