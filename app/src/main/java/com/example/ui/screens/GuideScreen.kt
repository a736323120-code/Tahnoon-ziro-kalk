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
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@Composable
fun GuideScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.language.collectAsState()

    val guideTitle = when (currentLang) {
        "en" -> "Quick Deployment Guide"
        "de" -> "Schnellstartanleitung"
        else -> "دليل الاستخدام السريع"
    }

    val steps = listOf(
        Triple(
            "1",
            when (currentLang) {
                "en" -> "Install Termux from F-Droid"
                "de" -> "Installieren Sie Termux von F-Droid"
                else -> "تثبيت تطبيق Termux من F-Droid"
            },
            when (currentLang) {
                "en" -> "Make sure to download the updated release directly from F-Droid, as the Play Store version is deprecated."
                "de" -> "Stellen Sie sicher, dass Sie das Update von F-Droid herunterladen, da die Play Store-Version veraltet ist."
                else -> "احرص على تحميل الإصدار الأحدث من متجر F-Droid المجاني مباشرة، حيث أن إصدار متجر Play Store قديم وغير مدعوم."
            }
        ),
        Triple(
            "2",
            when (currentLang) {
                "en" -> "Grant Storage Permission"
                "de" -> "Speicherzugriff gewähren"
                else -> "منح صلاحيات التخزين للبيئة"
            },
            when (currentLang) {
                "en" -> "Launch Termux and run the following command to link your local directories:\n\ntermux-setup-storage"
                "de" -> "Starten Sie Termux und führen Sie diesen Befehl aus, um die Ordner zu verknüpfen:\n\ntermux-setup-storage"
                else -> "افتح تطبيق Termux واكتب الأمر التالي لربط الذاكرة الداخلية للهاتف ببيئة العمل:\n\ntermux-setup-storage"
            }
        ),
        Triple(
            "3",
            when (currentLang) {
                "en" -> "Run Tahnoun Arsenal Bootstrapper"
                "de" -> "Tahnoun Arsenal Bootstrapper ausführen"
                else -> "تشغيل ترسانة طحنون للتطوير"
            },
            when (currentLang) {
                "en" -> "Navigate to the designated application workspace using the terminal:\n\ncd /sdcard/TahnounApp && python3 main.py"
                "de" -> "Navigieren Sie mit dem Terminal zum Anwendungsbereich:\n\ncd /sdcard/TahnounApp && python3 main.py"
                else -> "انتقل إلى مسار العمل الافتراضي للتطبيق داخل الطرفية من خلال الأمر التالي:\n\ncd /sdcard/TahnounApp && python3 main.py"
            }
        ),
        Triple(
            "4",
            when (currentLang) {
                "en" -> "Trigger Automated Tool deployment"
                "de" -> "Automatische Werkzeugbereitstellung"
                else -> "تفعيل وتثبيت الأدوات الأمنية"
            },
            when (currentLang) {
                "en" -> "Navigate to the Tools tab on your screen, choose your security tools category, and tap the install/download arrow. Zero Click will automatically provision it for you."
                "de" -> "Navigieren Sie zum Reiter Werkzeuge, wählen Sie die Kategorie aus und tippen Sie auf Herunterladen. Zero Click richtet es automatisch ein."
                else -> "انتقل لتبويب الأدوات في التطبيق، حدد التصنيف المناسب لك، ثم اضغط على زر التنزيل لتبدأ عملية الإعداد والتثبيت التلقائي."
            }
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF07090E))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = guideTitle,
                color = Color(0xFF33FF44),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(steps) { (num, stepTitle, stepDesc) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10141F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Numeric circle indicator badge
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFF33FF44)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = num,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = stepTitle,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stepDesc,
                        color = Color.Gray,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        textAlign = if (currentLang == "ar") TextAlign.Right else TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Final educational warning card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1C10)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFFFCC00).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning",
                        tint = Color(0xFFFFCC00),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = when (currentLang) {
                            "en" -> "Remember: Never audit or try to test services and networks without official legal approval."
                            "de" -> "Achtung: Überprüfen Sie niemals Dienste und Netzwerke ohne offizielle Genehmigung."
                            else -> "تذكر دائماً: يمنع منعاً باتاً فحص واختبار الشبكات والأنظمة بدون موافقة صريحة مكتوبة وقانونية."
                        },
                        color = Color(0xFFFFAA33),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
