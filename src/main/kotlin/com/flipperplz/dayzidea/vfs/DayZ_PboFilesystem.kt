package com.flipperplz.dayzidea.vfs

import com.flipperplz.dayzidea.DAYZ_PBO_PROTOCOL
import com.flipperplz.dayzidea.DAYZ_PBO_SEPARATOR
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.impl.ArchiveHandler
import com.intellij.openapi.vfs.newvfs.ArchiveFileSystem
import com.intellij.openapi.vfs.newvfs.VfsImplUtil

typealias PboFilesystem = DayZ_PboFilesystem
class DayZ_PboFilesystem : ArchiveFileSystem() {
    override fun isCorrectFileType(local: VirtualFile): Boolean = FileTypeRegistry.getInstance().getFileTypeByFileName(local.name) == PboFileType.instance
    override fun getProtocol(): String = DAYZ_PBO_PROTOCOL
    override fun findFileByPath(path: String): VirtualFile? = VfsImplUtil.findFileByPath(this, path)
    override fun refresh(asynchronous: Boolean) = VfsImplUtil.refresh(this, asynchronous)
    override fun refreshAndFindFileByPath(path: String): VirtualFile? = VfsImplUtil.refreshAndFindFileByPath(this, path)
    override fun findFileByPathIfCached(path: String): VirtualFile? = VfsImplUtil.findFileByPathIfCached(this, path)
    override fun extractLocalPath(rootPath: String): String = StringUtil.trimEnd(rootPath, DAYZ_PBO_SEPARATOR)
    override fun composeRootPath(localPath: String): String = localPath + DAYZ_PBO_SEPARATOR
    override fun getHandler(entryFile: VirtualFile): ArchiveHandler = VfsImplUtil.getHandler(this, entryFile) { PboHandler(it) }
    override fun extractRootPath(normalizedPath: String): String = with(normalizedPath.indexOf(DAYZ_PBO_SEPARATOR)) {
        if(this == -1) return normalizedPath
        else return normalizedPath.substring(0, this + DAYZ_PBO_SEPARATOR.length)
    }
    companion object {
        val instance: PboFilesystem get() = VirtualFileManager.getInstance().getFileSystem(DAYZ_PBO_PROTOCOL) as PboFilesystem

    }
}