package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ToolEntity
import com.example.viewmodel.MainViewModel

@Composable
fun AboutScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.language.collectAsState()

    val title = when (currentLang) {
        "en" -> "About Zero Click Suite"
        "de" -> "Über Zero Click Suite"
        else -> "حول تطبيق طحنون"
    }

    val subtitle = when (currentLang) {
        "en" -> "Advanced Penetration Testing Assistant"
        "de" -> "Fortgeschrittener Penetrationstest-Assistent"
        else -> "أدوات ومكتبات الأمن السيبراني المتقدمة"
    }

    val textBody = when (currentLang) {
        "en" -> """
            Zero Click Arsenal is a unified security manager and script compiler application for systems specialists and red team practitioners.
            
            Developed by the Tahnoun Security Team, this platform automates setup of packages, custom scripts, security wrappers, and terminal shortcuts under local environments.
            
            Suitable for bug bounty hunters, researchers, and professional operators. Please use responsibility.
        """.trimIndent()
        "de" -> """
            Das Zero Click Arsenal ist ein einheitlicher Sicherheitsmanager und Skript-Compiler für System-Spezialisten und Red-Teaming-Praktiker.
            
            Entwickelt vom Tahnoun-Sicherheitsteam automatisiert diese Plattform das Setup von Paketen, Skripten und Terminal-Verknüpfungen in lokalen Umgebungen.
            
            Geeignet für Bug-Bounty-Jäger und Forscher. Bitte verantwortungsbewusst nutzen.
        """.trimIndent()
        else -> """
            طحنون للتطوير - Zero Click Arsenal هو تطبيق متكامل ومتقدم لإدارة وتثبيت أدوات الأمن السيبراني واختبار الاختراق على أجهزة الأندرويد وبيئة Termux بالكامل.
            
            يهدف التطبيق الذي تم تطويره بواسطة فريق طحنون للأمن السيبراني إلى تسهيل إعداد بيئات الفحص الأمني، والمحاكاة لخبراء الـ Red Team وباحثي الثغرات الأمنية في الوطن العربي.
            
            يحتوي التطبيق على أكثر من 50 أداة ومكتبة في 8 تصنيفات متنوعة تدعم التثبيت بنقرة واحدة والتنفيذ الآمن.
        """.trimIndent()
    }

    val footerDisclaimer = when (currentLang) {
        "en" -> "⚠️ Dislaimer: Educational and ethical purposes only. Unauthorized testing is illegal."
        "de" -> "⚠️ Haftungsausschluss: Nur für pädagogische und ethische Zwecke."
        else -> "⚠️ تنبيه هام: هذا التطبيق مخصص للأغراض التعليمية والأخلاقية فقط. يمنع الاستخدام غير القانوني."
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090E))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF33FF44).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon),
                        contentDescription = "App Icon Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(2.dp, Color(0xFF33FF44), RoundedCornerShape(20.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = title,
                        color = Color(0xFF33FF44),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = subtitle,
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "v2.0.0 (Zero Click Edition)",
                        color = Color(0xFFFFCC00),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = when (currentLang) {
                            "en" -> "About Tahnoun Arsenal"
                            "de" -> "Über Tahnoun Arsenal"
                            else -> "عن ترسانة طحنون"
                        },
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = textBody,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        textAlign = if (currentLang == "ar") TextAlign.Right else TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF261010)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Red.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning Disclaimer",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = footerDisclaimer,
                        color = Color(0xFFFFAAAA),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            Text(
                text = "© 2026 Tahnoun Security Team. All Rights Reserved.",
                color = Color.DarkGray,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}
