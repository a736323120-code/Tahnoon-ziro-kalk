package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ToolEntity
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.language.collectAsState()
    val allTools by viewModel.allTools.collectAsState()
    val activeCategory by viewModel.selectedCategory.collectAsState()
    val isInstallingId by viewModel.isInstalling.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedToolForDetail by remember { mutableStateOf<ToolEntity?>(null) }

    // Multi-language translation helper
    val categoriesMap = listOf(
        Triple("ALL", if (currentLang == "ar") "الكل" else if (currentLang == "de") "Alle" else "All", "cat_all"),
        Triple("BASIC", if (currentLang == "ar") "الأساسية" else if (currentLang == "de") "Grundlagen" else "Basic Tools", "cat_basic"),
        Triple("PYTHON_LIBS", if (currentLang == "ar") "مكتبات بايثون" else if (currentLang == "de") "Python Bibs" else "Python Libs", "cat_python"),
        Triple("FRAMEWORKS", if (currentLang == "ar") "أطر عمل" else if (currentLang == "de") "Frameworks" else "Frameworks", "cat_framework"),
        Triple("NETWORK", if (currentLang == "ar") "شبكات" else if (currentLang == "de") "Netzwerk" else "Network", "cat_network"),
        Triple("PASSWORDS", if (currentLang == "ar") "كلمات المرور" else if (currentLang == "de") "Passwörter" else "Passwords", "cat_pass"),
        Triple("REVERSE", if (currentLang == "ar") "هندسة عكسية" else if (currentLang == "de") "Reverse" else "Reverse Eng", "cat_reverse"),
        Triple("WEB", if (currentLang == "ar") "ويب" else if (currentLang == "de") "Web-Tools" else "Web Tools", "cat_web"),
        Triple("WIRELESS", if (currentLang == "ar") "لاسلكي" else if (currentLang == "de") "Drahtlos" else "Wireless", "cat_wireless")
    )

    val filteredTools = allTools.filter { tool ->
        val nameMatches = tool.nameAr.contains(searchQuery, ignoreCase = true) || 
                          tool.nameEn.contains(searchQuery, ignoreCase = true) ||
                          tool.id.contains(searchQuery, ignoreCase = true)
        val categoryMatches = activeCategory == "ALL" || tool.category == activeCategory
        nameMatches && categoryMatches
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090E))
    ) {
        // Banner Hero section with Cyberpunk graphic
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_cyber_illustration),
                contentDescription = "Security Banner Hero",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xFF07090E)),
                            startY = 0f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = if (currentLang == "ar") "ترسانة الأدوات الأمنية" else if (currentLang == "de") "Werkzeuge Arsenal" else "Security Arsenal",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (currentLang == "ar") "تثبيت بنقرة واحدة وتجهيز آلي" else if (currentLang == "de") "1-Klick Installation & Einrichtung" else "One-click install & environment provisioning",
                    color = Color(0xFF33FF44),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = if (currentLang == "ar") "ابحث عن أداة معينة..." else if (currentLang == "de") "Suchen Sie ein Werkzeug..." else "Search secure tools...",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = Color.Gray)
                    }
                }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF33FF44),
                unfocusedBorderColor = Color.DarkGray,
                focusedContainerColor = Color(0xFF10141F),
                unfocusedContainerColor = Color(0xFF10141F),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("tool_search_bar")
        )

        // Horizontal Scrollable Category Filter Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categoriesMap) { (categoryCode, displayName, testTagId) ->
                val isSelected = activeCategory == categoryCode
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFF33FF44) else Color(0xFF10141F))
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color(0xFF33FF44) else Color.DarkGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { viewModel.setCategory(categoryCode) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag(testTagId)
                ) {
                    Text(
                        text = displayName,
                        color = if (isSelected) Color.Black else Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // List of Tools
        if (filteredTools.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (currentLang == "ar") "لا توجد أدوات تطابق الفلتر الحالي" else if (currentLang == "de") "Keine Werkzeuge gefunden" else "No security tools match search query",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredTools, key = { it.id }) { tool ->
                    val isInstallingThis = isInstallingId == tool.id
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
                            .clickable { selectedToolForDetail = tool }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Category Icon Indicator Badge
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (tool.isInstalled) Color(0xFF103A19) else Color(
                                            0xFF1A1F2C
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when (tool.category) {
                                        "BASIC" -> Icons.Default.List
                                        "PYTHON_LIBS" -> Icons.Default.Build
                                        "FRAMEWORKS" -> Icons.Default.PlayArrow
                                        "NETWORK" -> Icons.Default.Refresh
                                        "PASSWORDS" -> Icons.Default.Lock
                                        "REVERSE" -> Icons.Default.Search
                                        "WEB" -> Icons.Default.Info
                                        else -> Icons.Default.Settings
                                    },
                                    contentDescription = null,
                                    tint = if (tool.isInstalled) Color(0xFF33FF44) else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Name & Description (Shortened)
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = if (currentLang == "ar") tool.nameAr else tool.nameEn,
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    if (tool.isInstalled) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFF33FF44).copy(alpha = 0.2f))
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "INSTALLED",
                                                color = Color(0xFF33FF44),
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = if (currentLang == "ar") tool.descriptionAr else tool.descriptionEn,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            // Favorite and Install controls
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { viewModel.toggleFavorite(tool.id, tool.isFavorite) }
                                ) {
                                    Icon(
                                        imageVector = if (tool.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Toggle Favorite",
                                        tint = if (tool.isFavorite) Color.Red else Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                if (isInstallingThis) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color(0xFF33FF44),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    IconButton(
                                        onClick = {
                                            if (tool.isInstalled) {
                                                viewModel.uninstallTool(tool)
                                            } else {
                                                viewModel.installTool(tool)
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (tool.isInstalled) Icons.Default.Delete else Icons.Default.PlayArrow,
                                            contentDescription = if (tool.isInstalled) "Uninstall" else "Install",
                                            tint = if (tool.isInstalled) Color.Red.copy(alpha = 0.8f) else Color(0xFF33FF44),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal detail bottom sheet
    selectedToolForDetail?.let { tool ->
        AlertDialog(
            onDismissRequest = { selectedToolForDetail = null },
            containerColor = Color(0xFF10141F),
            modifier = Modifier.border(1.dp, Color(0xFF33FF44).copy(alpha = 0.3f), RoundedCornerShape(28.dp)),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF33FF44).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = Color(0xFF33FF44),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (currentLang == "ar") tool.nameAr else tool.nameEn,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = if (currentLang == "ar") "وصف الأداة المتكامل:" else "Integrated Tool Description:",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (currentLang == "ar") tool.descriptionAr else tool.descriptionEn,
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = if (currentLang == "ar") TextAlign.Right else TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (currentLang == "ar") "أمر التثبيت المستخدم:" else "Command applied to deploy:",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF07090E))
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(6.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = tool.installCommand,
                            color = Color(0xFF33FF44),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text(
                            text = "Category: ",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Text(
                            text = tool.category,
                            color = Color(0xFFFFCC00),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (tool.isInstalled && tool.installedAt != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row {
                            Text(
                                text = "Installed At: ",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                            Text(
                                text = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date(tool.installedAt)),
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (tool.isInstalled) {
                            viewModel.uninstallTool(tool)
                        } else {
                            viewModel.installTool(tool)
                        }
                        selectedToolForDetail = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tool.isInstalled) Color.Red else Color(0xFF33FF44),
                        contentColor = if (tool.isInstalled) Color.White else Color.Black
                    )
                ) {
                    Text(
                        text = if (tool.isInstalled) {
                            if (currentLang == "ar") "إلغاء التثبيت" else if (currentLang == "de") "Deinstallieren" else "Uninstall"
                        } else {
                            if (currentLang == "ar") "تثبيت الآن" else if (currentLang == "de") "Installieren" else "Install Now"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedToolForDetail = null }) {
                    Text(
                        text = if (currentLang == "ar") "إغلاق" else if (currentLang == "de") "Schließen" else "Close",
                        color = Color.Gray
                    )
                }
            }
        )
    }
}
