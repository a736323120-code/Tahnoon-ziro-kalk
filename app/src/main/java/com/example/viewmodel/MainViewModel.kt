package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ToolEntity
import com.example.data.ToolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ToolRepository) : ViewModel() {

    // Language configuration: 'ar', 'en', 'de'
    private val _language = MutableStateFlow("ar")
    val language: StateFlow<String> = _language.asStateFlow()

    // Setup active category selection
    private val _selectedCategory = MutableStateFlow("ALL")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Setup terminal execution output logs emulation
    private val _terminalLogs = MutableStateFlow<List<String>>(
        listOf(
            "===========================================",
            "🤖 Zero Click Arsenal CLI initialized successfully",
            "🛡️ Tahnoun Security Lab kernel: active",
            "==========================================="
        )
    )
    val terminalLogs: StateFlow<List<String>> = _terminalLogs.asStateFlow()

    private val _isInstalling = MutableStateFlow<String?>(null)
    val isInstalling: StateFlow<String?> = _isInstalling.asStateFlow()

    // Load reactive tools list from Repository
    val allTools: StateFlow<List<ToolEntity>> = repository.allTools
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val installedTools: StateFlow<List<ToolEntity>> = repository.installedTools
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val favoriteTools: StateFlow<List<ToolEntity>> = repository.favoriteTools
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.populateInitialToolsIfEmpty()
        }
    }

    fun setLanguage(lang: String) {
        _language.value = lang
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleFavorite(toolId: String, currentFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(toolId, currentFavorite)
        }
    }

    fun installTool(tool: ToolEntity) {
        viewModelScope.launch {
            _isInstalling.value = tool.id
            appendLog("\n⚡ Preparing environment to install: ${tool.nameEn}...")
            appendLog("📂 Workspace set: /sdcard/TahnounApp")
            appendLog("🛰️ Querying dependencies for ${tool.id}...")
            appendLog("📥 Running: ${tool.installCommand}")
            
            // Emulate terminal download progress bar and installation steps
            kotlinx.coroutines.delay(600)
            appendLog("📶 [34%] Connected to software mirror. Downloading headers...")
            kotlinx.coroutines.delay(800)
            appendLog("📦 [72%] Unpacking binary packages & resolving hashes...")
            kotlinx.coroutines.delay(700)
            appendLog("🔧 [95%] Configuring symlinks and PATH variables...")
            kotlinx.coroutines.delay(500)
            
            repository.updateInstallStatus(tool.id, true)
            _isInstalling.value = null
            appendLog("✅ Installation successful: ${tool.nameEn} matches latest version!")
            appendLog("🚀 Run 'tahnoun' or direct system wrapper shortcut.")
        }
    }

    fun uninstallTool(tool: ToolEntity) {
        viewModelScope.launch {
            appendLog("\n🗑️ Removing binaries and assets for: ${tool.nameEn}...")
            repository.updateInstallStatus(tool.id, false)
            kotlinx.coroutines.delay(500)
            appendLog("♻️ Cleaned up workspace files safely.")
        }
    }

    fun runDiagnostic() {
        viewModelScope.launch {
            appendLog("\n🔍 RUNNING FULL SYSTEM RECON & INTEGRITY CHECKS...")
            kotlinx.coroutines.delay(400)
            appendLog("📡 Network Link: OK (Connected to secure lab)")
            kotlinx.coroutines.delay(400)
            appendLog("🛡️ Sandbox Level: Safe environment validated")
            kotlinx.coroutines.delay(400)
            appendLog("📂 Shared Dir: /sdcard/TahnounApp exists")
            appendLog("🐍 System Python: Python v3.11.x active")
            appendLog("✅ Diagnostic suite finished. All green!")
        }
    }

    fun clearLogs() {
        _terminalLogs.value = listOf(
            "===========================================",
            "♻️ Terminal history wiped safely.",
            "🤖 Ready for next instructions.",
            "==========================================="
        )
    }

    private fun appendLog(msg: String) {
        val current = _terminalLogs.value.toMutableList()
        current.add(msg)
        if (current.size > 120) {
            current.removeAt(0)
        }
        _terminalLogs.value = current
    }
}

class MainViewModelFactory(private val repository: ToolRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
