package com.flipperplz.dayzidea.vfs

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.FileType
import javax.swing.Icon

typealias PboFileType = DayZ_PboFileType;
object DayZ_PboFileType : FileType {
    override fun getName(): String = "PBO"
    override fun getDescription(): String = "PBO Archive"
    override fun getDefaultExtension(): String = "pbo"
    override fun getIcon(): Icon = AllIcons.FileTypes.Archive
    override fun isBinary(): Boolean = true
    override fun isReadOnly(): Boolean = true
}
