package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "installed_tools")
data class ToolEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val category: String,
    val installCommand: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val isInstalled: Boolean = false,
    val installedAt: Long? = null,
    val isFavorite: Boolean = false
)
