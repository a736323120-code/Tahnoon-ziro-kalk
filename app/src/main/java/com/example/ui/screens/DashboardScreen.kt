package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ToolEntity
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.language.collectAsState()
    val allTools by viewModel.allTools.collectAsState()
    val installedTools by viewModel.installedTools.collectAsState()
    val favoriteTools by viewModel.favoriteTools.collectAsState()
    val terminalLogs by viewModel.terminalLogs.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val logsListState = rememberLazyListState()

    // Scroll automatically to bottom of the live terminal feed when logs change
    LaunchedEffect(terminalLogs.size) {
        if (terminalLogs.isNotEmpty()) {
            logsListState.animateScrollToItem(terminalLogs.size - 1)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090E))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome and Stats Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF33FF44).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (currentLang == "ar") "أهلاً بك في ترسانة طحنون" else "Welcome to Tahnoun Arsenal",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Zero Click Security Suite — Android Wrapper Edition",
                        color = Color(0xFF33FF44),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simple stats grid
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Total tools
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF161B2B))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = if (currentLang == "ar") "إجمالي الأدوات" else "Total Tools",
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "${allTools.size}",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        // Installed tools
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF161B2B))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = if (currentLang == "ar") "الأدوات المثبتة" else "Installed",
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "${installedTools.size}",
                                    color = Color(0xFF33FF44),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        // Favorites
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF161B2B))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = if (currentLang == "ar") "المفضلة" else "Favorites",
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "${favoriteTools.size}",
                                    color = Color.Red,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Live emulation Terminal Console log feed
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (currentLang == "ar") "طرفية التشغيل المباشرة (Terminal Logs)" else "Live Terminal Logs Emulation",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { viewModel.clearLogs() }) {
                        Text(
                            text = if (currentLang == "ar") "تصفير" else "Reset",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF030508))
                        .border(1.dp, Color(0xFF33FF44).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    LazyColumn(
                        state = logsListState,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(terminalLogs) { logLine ->
                            Text(
                                text = logLine,
                                color = if (logLine.startsWith("✅") || logLine.contains("successful")) Color(0xFF33FF44)
                                else if (logLine.startsWith("❌") || logLine.contains("Error")) Color.Red
                                else if (logLine.startsWith("⚡") || logLine.contains("Preparing")) Color(0xFFFFCC00)
                                else Color.LightGray,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // Favorites and quick launchers
        item {
            Text(
                text = if (currentLang == "ar") "المفضلة والوصول السريع" else "Favorite & Quick Launcher",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (favoriteTools.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentLang == "ar") "لا توجد أدوات في المفضلة حالياً.\nقم بزيارة صفحة الأدوات لإضافتها." else "No favorite tools added yet.\nVisit the tools page to add any.",
                            color = Color.Gray,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(favoriteTools, key = { it.id }) { tool ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF2B161B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (currentLang == "ar") tool.nameAr else tool.nameEn,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Category: ${tool.category}",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )
                        }

                        // Short Launch execution emulator
                        Button(
                            onClick = {
                                if (tool.isInstalled) {
                                    viewModel.installTool(tool) // acts as run/re-execute emulator
                                } else {
                                    viewModel.installTool(tool)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2436)),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = if (tool.isInstalled) {
                                    if (currentLang == "ar") "تشغيل" else "Run"
                                } else {
                                    if (currentLang == "ar") "تثبيت" else "Install"
                                },
                                fontSize = 11.sp,
                                color = Color(0xFF33FF44)
                            )
                        }
                    }
                }
            }
        }
    }
}
