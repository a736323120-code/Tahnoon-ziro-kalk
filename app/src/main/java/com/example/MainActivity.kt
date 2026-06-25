package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.ToolRepository
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GuideScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Local SQLite Room database initialization
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tahnoun_cyber_arsenal.db"
        ).fallbackToDestructiveMigration().build()

        val repository = ToolRepository(database.toolDao())
        val viewModelFactory = MainViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                MainAppContainer(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(viewModel: MainViewModel) {
    val currentLang by viewModel.language.collectAsState()

    // 0: Dashboard/Terminal, 1: Tools, 2: Guide, 3: About, 4: Settings
    var currentTab by remember { mutableStateOf(0) }

    val appTitle = when (currentLang) {
        "en" -> "Tahnoun Arsenal"
        "de" -> "Tahnoun Arsenal"
        else -> "طحنون للتطوير"
    }

    val tabs = listOf(
        Triple(
            if (currentLang == "ar") "الرئيسية" else if (currentLang == "de") "Dashboard" else "Dashboard",
            Icons.Default.Home,
            "tab_dashboard"
        ),
        Triple(
            if (currentLang == "ar") "الأدوات" else if (currentLang == "de") "Werkzeuge" else "Tools",
            Icons.Default.PlayArrow,
            "tab_tools"
        ),
        Triple(
            if (currentLang == "ar") "الدليل" else if (currentLang == "de") "Anleitung" else "Guide",
            Icons.Default.Menu,
            "tab_guide"
        ),
        Triple(
            if (currentLang == "ar") "حول" else if (currentLang == "de") "Über" else "About",
            Icons.Default.Info,
            "tab_about"
        ),
        Triple(
            if (currentLang == "ar") "الإعدادات" else if (currentLang == "de") "Einstell." else "Settings",
            Icons.Default.Settings,
            "tab_settings"
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = appTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF33FF44)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0A0D15),
                    titleContentColor = Color(0xFF33FF44)
                ),
                modifier = Modifier.testTag("app_top_bar")
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0A0D15),
                contentColor = Color.LightGray,
                modifier = Modifier.testTag("app_bottom_navigation")
            ) {
                tabs.forEachIndexed { index, (label, icon, testTagId) ->
                    NavigationBarItem(
                        selected = currentTab == index,
                        onClick = { currentTab = index },
                        icon = { Icon(imageVector = icon, contentDescription = label) },
                        label = { Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFF33FF44),
                            indicatorColor = Color(0xFF33FF44),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        ),
                        modifier = Modifier.testTag(testTagId)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF07090E))
                .padding(innerPadding)
        ) {
            when (currentTab) {
                0 -> DashboardScreen(viewModel = viewModel)
                1 -> ToolsScreen(viewModel = viewModel)
                2 -> GuideScreen(viewModel = viewModel)
                3 -> AboutScreen(viewModel = viewModel)
                4 -> SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
