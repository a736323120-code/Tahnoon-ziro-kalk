package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.language.collectAsState()
    val terminalHistory by viewModel.terminalLogs.collectAsState()

    var showClearConfirm by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090E))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = when (currentLang) {
                    "en" -> "Configuration & Environment"
                    "de" -> "Konfiguration & Umgebung"
                    else -> "إعدادات البيئة والتطبيق"
                },
                color = Color(0xFF33FF44),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Language Chooser Section
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF33FF44))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (currentLang) {
                                "en" -> "Display Language"
                                "de" -> "Sprache wählen"
                                else -> "لغة عرض التطبيق"
                            },
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(
                            Triple("ar", "العربية 🇸🇦", "ar_lang_btn"),
                            Triple("en", "English 🇬🇧", "en_lang_btn"),
                            Triple("de", "Deutsch 🇩🇪", "de_lang_btn")
                        ).forEach { (code, label, tag) ->
                            val isSelected = currentLang == code
                            Button(
                                onClick = { viewModel.setLanguage(code) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Color(0xFF33FF44) else Color(0xFF1D2436),
                                    contentColor = if (isSelected) Color.Black else Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag(tag),
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Workspace settings
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF33FF44))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (currentLang) {
                                "en" -> "Virtual Workspace Root"
                                "de" -> "Virtueller Arbeitsbereich"
                                else -> "مسار بيئة العمل الافتراضية"
                            },
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = "/sdcard/TahnounApp",
                        onValueChange = {},
                        readOnly = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF33FF44),
                            unfocusedBorderColor = Color.DarkGray,
                            focusedContainerColor = Color(0xFF0B0E14),
                            unfocusedContainerColor = Color(0xFF0B0E14),
                            focusedTextColor = Color.LightGray,
                            unfocusedTextColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = when (currentLang) {
                            "en" -> "All tools reference scripts are localized within this safe sandbox."
                            "de" -> "Alle Werkzeuge-Skripte sind in dieser sicheren Sandbox lokalisiert."
                            else -> "توضع وتدار جميع السكربتات والأدوات داخل هذا المجلد الآمن."
                        },
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Quick Lab Utilities
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = when (currentLang) {
                            "en" -> "Diagnostic & Toolkit Actions"
                            "de" -> "Diagnose & Aktionen"
                            else -> "إجراءات الفحص والتشخيص"
                        },
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { viewModel.runDiagnostic() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2436)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("run_diagnostic_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF33FF44))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = when (currentLang) {
                                        "en" -> "Diagnostic"
                                        "de" -> "Diagnose"
                                        else -> "فحص النظام"
                                    },
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Button(
                            onClick = { showClearConfirm = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E1C1F)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("clear_terminal_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Red)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = when (currentLang) {
                                        "en" -> "Clear Logs"
                                        "de" -> "Verlauf löschen"
                                        else -> "مسح السجل"
                                    },
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            containerColor = Color(0xFF121622),
            title = {
                Text(
                    text = when (currentLang) {
                        "en" -> "Clear Console Logs"
                        "de" -> "Konsolenprotokolle löschen"
                        else -> "مسح سجل الأوامر والتنبيهات"
                    },
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = when (currentLang) {
                        "en" -> "Are you sure you want to clear the active terminal emulator log history?"
                        "de" -> "Möchten Sie den Verlauf des Terminal-Emulators wirklich löschen?"
                        else -> "هل أنت متأكد من مسح جميع التقارير وسجلات التشغيل في الطرفية؟"
                    },
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearLogs()
                        showClearConfirm = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(
                        text = when (currentLang) {
                            "en" -> "Clear"
                            "de" -> "Löschen"
                            else -> "تأكيد المسح"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showClearConfirm = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                ) {
                    Text(
                        text = when (currentLang) {
                            "en" -> "Cancel"
                            "de" -> "Abbrechen"
                            else -> "إلغاء"
                        }
                    )
                }
            }
        )
    }
}
