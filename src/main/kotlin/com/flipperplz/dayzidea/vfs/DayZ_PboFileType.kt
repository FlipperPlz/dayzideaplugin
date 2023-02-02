package com.flipperplz.dayzidea.vfs

import com.intellij.ide.highlighter.ArchiveFileType

typealias PboFileType = DayZ_PboFileType;
class DayZ_PboFileType : ArchiveFileType() {
    override fun getDefaultExtension(): String = "pbo"
    override fun getDisplayName(): String = "PBO Archive"
    override fun getDescription(): String = "An archive format created by Bohemia Interactive, used for Arma and DayZ games"
    override fun getName(): String = "PBO"
    companion object {
        val instance = PboFileType()
    }
}
