package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolDao {
    @Query("SELECT * FROM installed_tools")
    fun getAllTools(): Flow<List<ToolEntity>>

    @Query("SELECT * FROM installed_tools WHERE category = :category")
    fun getToolsByCategory(category: String): Flow<List<ToolEntity>>

    @Query("SELECT * FROM installed_tools WHERE isInstalled = 1")
    fun getInstalledTools(): Flow<List<ToolEntity>>

    @Query("SELECT * FROM installed_tools WHERE isFavorite = 1")
    fun getFavoriteTools(): Flow<List<ToolEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTools(tools: List<ToolEntity>)

    @Query("UPDATE installed_tools SET isInstalled = :installed, installedAt = :installedAt WHERE id = :toolId")
    suspend fun updateInstallStatus(toolId: String, installed: Boolean, installedAt: Long?)

    @Query("UPDATE installed_tools SET isFavorite = :isFavorite WHERE id = :toolId")
    suspend fun updateFavoriteStatus(toolId: String, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM installed_tools")
    suspend fun getCount(): Int
}
