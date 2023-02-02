package com.flipperplz.dayzidea.vfs

import com.flipperplz.bisutils.pbo.PboFile
import com.intellij.openapi.util.io.FileSystemUtil
import com.intellij.openapi.vfs.impl.ArchiveHandler
import com.intellij.util.io.FileAccessorCache
import com.intellij.util.io.ResourceHandle
import java.io.FileNotFoundException

typealias PboHandler = DayZ_PboHandler;
class DayZ_PboHandler(path: String) : ArchiveHandler(path) {
    @Volatile private var myFileTimestamp: Long = DEFAULT_TIMESTAMP
    @Volatile private var myFileLength: Long = DEFAULT_LENGTH

    private fun getPboFileHandle(): ResourceHandle<PboFile> {
        val handle = myAccessorCache[this]
        val attributes = file.canonicalFile.let { FileSystemUtil.getAttributes(it) ?: throw FileNotFoundException(it.toString()) }
        if (attributes.lastModified == myFileTimestamp && attributes.length == myFileLength) return handle
        myAccessorCache.remove(this)
        handle.release()
        return myAccessorCache[this]
    }
    override fun createEntriesMap(): MutableMap<String, EntryInfo> = getPboFileHandle().use {
        val pbo = it.get(); val map = HashMap<String, EntryInfo>()
        map[""] = createRootEntry()
        pbo.getWrittenDataEntries().forEach { entry ->
            fun getOrCreate(entryName: String, isDirectory: Boolean = false): EntryInfo = map[entryName] ?:
            EntryInfo(entryName.substringAfterLast('\\'), isDirectory, entry.originalSize, entry.timestamp,
                getOrCreate(entryName.substringBeforeLast('\\')));
            getOrCreate(entry.entryPath.absoluteNormalizedPath)
        }
        map
    }
    override fun contentsToByteArray(relativePath: String): ByteArray = getPboFileHandle().use { pboResourceHandle ->
        val pbo = pboResourceHandle.get();
        return pbo.getEntryData(pbo.getWrittenDataEntries().firstOrNull { it.entryPath.equals(relativePath)} ?:
        throw Exception("Failed to find $relativePath in pbo file!"), false)
    }
    companion object {
        private val myAccessorCache = object : FileAccessorCache<PboHandler, PboFile>(20, 10) {
            override fun disposeAccessor(fileAccessor: PboFile) {}
            override fun createAccessor(key: PboHandler): PboFile {
                FileSystemUtil.getAttributes(key.file.canonicalFile).let {
                    key.myFileTimestamp = it?.lastModified ?: DEFAULT_TIMESTAMP
                    key.myFileLength= it?.length ?: DEFAULT_LENGTH
                    return PboFile.fromFile(key.file.canonicalFile)
                }
            }
        }
    }
}