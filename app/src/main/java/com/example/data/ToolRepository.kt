package com.example.data

import kotlinx.coroutines.flow.Flow

class ToolRepository(private val toolDao: ToolDao) {
    val allTools: Flow<List<ToolEntity>> = toolDao.getAllTools()
    val installedTools: Flow<List<ToolEntity>> = toolDao.getInstalledTools()
    val favoriteTools: Flow<List<ToolEntity>> = toolDao.getFavoriteTools()

    fun getToolsByCategory(category: String): Flow<List<ToolEntity>> {
        return toolDao.getToolsByCategory(category)
    }

    suspend fun populateInitialToolsIfEmpty() {
        if (toolDao.getCount() == 0) {
            val initialList = listOf(
                // Category: BASIC
                ToolEntity(
                    id = "python",
                    nameAr = "بايثون (Python)",
                    nameEn = "Python Interpreter",
                    category = "BASIC",
                    installCommand = "pkg install python -y",
                    descriptionAr = "مفسر لغة بايثون الأساسي اللازم لتشغيل معظم نصوص الأمن البرمجية.",
                    descriptionEn = "Essential python interpreter needed to execute most security scripts."
                ),
                ToolEntity(
                    id = "git",
                    nameAr = "جيت (Git)",
                    nameEn = "Git Version Control",
                    category = "BASIC",
                    installCommand = "pkg install git -y",
                    descriptionAr = "نظام التحكم في الإصدارات لتحميل وتحديث الأدوات البرمجية من GitHub.",
                    descriptionEn = "Version control system to download and update tools from GitHub."
                ),
                ToolEntity(
                    id = "vim",
                    nameAr = "فيم (Vim)",
                    nameEn = "Vim Editor",
                    category = "BASIC",
                    installCommand = "pkg install vim -y",
                    descriptionAr = "محرر نصوص متقدم وقوي للتحرير السريع للملفات داخل سطر الأوامر.",
                    descriptionEn = "Advanced and powerful terminal text editor for quick modifications."
                ),
                ToolEntity(
                    id = "ssh",
                    nameAr = "بروتوكول SSH (OpenSSH)",
                    nameEn = "OpenSSH",
                    category = "BASIC",
                    installCommand = "pkg install openssh -y",
                    descriptionAr = "الاتصال الآمن بالخوادم والأنظمة البعيدة والتحكم بها بالكامل.",
                    descriptionEn = "Securely connect and completely control remote servers and devices."
                ),
                ToolEntity(
                    id = "curl",
                    nameAr = "أداة Curl",
                    nameEn = "Curl Client",
                    category = "BASIC",
                    installCommand = "pkg install curl wget -y",
                    descriptionAr = "نقل البيانات والملفات من وإلى الخوادم عبر سطر الأوامر.",
                    descriptionEn = "Command-line tool for transferring data with URLs."
                ),

                // Category: PYTHON_LIBS
                ToolEntity(
                    id = "pwntools",
                    nameAr = "مكتبة Pwntools",
                    nameEn = "Pwntools Library",
                    category = "PYTHON_LIBS",
                    installCommand = "pip install pwntools",
                    descriptionAr = "إطار عمل مخصص لتطوير استغلال الثغرات وتسهيل الهندسة العكسية.",
                    descriptionEn = "Exploit development framework used for rapid prototyping and CTFs."
                ),
                ToolEntity(
                    id = "scapy",
                    nameAr = "مكتبة Scapy",
                    nameEn = "Scapy Packet Crafter",
                    category = "PYTHON_LIBS",
                    installCommand = "pip install scapy",
                    descriptionAr = "مكتبة قوية للتلاعب بحزم الشبكات وفحصها وإرسالها واستقبالها.",
                    descriptionEn = "Powerful interactive packet manipulation and sniffing program."
                ),
                ToolEntity(
                    id = "impacket",
                    nameAr = "مكتبة Impacket",
                    nameEn = "Impacket Framework",
                    category = "PYTHON_LIBS",
                    installCommand = "pip install impacket",
                    descriptionAr = "مجموعة من فئات البايثون للتعامل مع بروتوكولات الشبكات مثل SMB, MSRPC.",
                    descriptionEn = "Collection of Python classes for working with network protocols."
                ),
                ToolEntity(
                    id = "requests",
                    nameAr = "مكتبة Requests",
                    nameEn = "Requests HTTP Lib",
                    category = "PYTHON_LIBS",
                    installCommand = "pip install requests",
                    descriptionAr = "المكتبة القياسية الفعالة للتعامل مع طلبات الويب المتنوعة بسهولة كاملة.",
                    descriptionEn = "De facto standard library for making clean HTTP requests."
                ),

                // Category: FRAMEWORKS
                ToolEntity(
                    id = "metasploit",
                    nameAr = "ميتاسبلويت (Metasploit)",
                    nameEn = "Metasploit Framework",
                    category = "FRAMEWORKS",
                    installCommand = "pkg install metasploit -y",
                    descriptionAr = "إطار العمل الأقوى والأشهر عالمياً لاختبار الاختراق وتجربة الثغرات الأمنية.",
                    descriptionEn = "The premier global framework for developing and executing exploit code."
                ),
                ToolEntity(
                    id = "empire",
                    nameAr = "إمباير (Empire C2)",
                    nameEn = "Empire Framework",
                    category = "FRAMEWORKS",
                    installCommand = "git clone https://github.com/BC-SECURITY/Empire.git",
                    descriptionAr = "إطار عمل قوي للتحكم والسيطرة (C2) والتحرك الجانبي داخل الشبكات.",
                    descriptionEn = "Post-exploitation and command & control (C2) framework."
                ),

                // Category: NETWORK
                ToolEntity(
                    id = "nmap",
                    nameAr = "إن ماب (Nmap)",
                    nameEn = "Nmap Scanner",
                    category = "NETWORK",
                    installCommand = "pkg install nmap -y",
                    descriptionAr = "الأداة الأسطورية لفحص منافذ الشبكة والتعرف على الخدمات والأنظمة النشطة.",
                    descriptionEn = "Legendary tool for network discovery and vulnerability scanning."
                ),
                ToolEntity(
                    id = "bettercap",
                    nameAr = "بيتر كاب (Bettercap)",
                    nameEn = "Bettercap Suite",
                    category = "NETWORK",
                    installCommand = "pkg install bettercap -y",
                    descriptionAr = "أداة احترافية كاملة لمراقبة وفحص وهندسة الشبكات اللاسلكية والذكية.",
                    descriptionEn = "The Swiss Army knife for network reconnaissance and MITM attacks."
                ),
                ToolEntity(
                    id = "tcpdump",
                    nameAr = "تي سي بي دامب (tcpdump)",
                    nameEn = "tcpdump Sniffer",
                    category = "NETWORK",
                    installCommand = "pkg install tcpdump -y",
                    descriptionAr = "أداة كلاسيكية قوية لالتقاط وتحليل حركة مرور البيانات داخل الشبكة.",
                    descriptionEn = "Powerful command-line packet analyzer and network sniffer."
                ),

                // Category: PASSWORDS
                ToolEntity(
                    id = "john",
                    nameAr = "جون ذا ريبر (John)",
                    nameEn = "John the Ripper",
                    category = "PASSWORDS",
                    installCommand = "pkg install john -y",
                    descriptionAr = "أداة متخصصة وسريعة للغاية لكسر تشفير كلمات المرور وتخمينها.",
                    descriptionEn = "Fast password cracker, supporting hundreds of hash and cipher types."
                ),
                ToolEntity(
                    id = "hydra",
                    nameAr = "هيدرا (Hydra)",
                    nameEn = "Hydra Login Cracker",
                    category = "PASSWORDS",
                    installCommand = "pkg install hydra -y",
                    descriptionAr = "مخمن كلمات مرور متوازي وسريع يدعم بروتوكولات عديدة كـ SSH, FTP, HTTP.",
                    descriptionEn = "Very fast network logon cracker supporting numerous protocols."
                ),
                ToolEntity(
                    id = "hashcat",
                    nameAr = "هاش كات (Hashcat)",
                    nameEn = "Hashcat Engine",
                    category = "PASSWORDS",
                    installCommand = "pkg install hashcat -y",
                    descriptionAr = "أسرع كاسر كلمات مرور في العالم يعتمد بشكل كامل على المعالجات الرسومية.",
                    descriptionEn = "The world's fastest password recovery utility utilizing GPUs/CPUs."
                ),

                // Category: REVERSE
                ToolEntity(
                    id = "radare2",
                    nameAr = "راداري 2 (Radare2)",
                    nameEn = "Radare2 Framework",
                    category = "REVERSE",
                    installCommand = "pkg install radare2 -y",
                    descriptionAr = "إطار عمل كامل ومفتوح المصدر للهندسة العكسية وتحليل الأكواد الثنائية.",
                    descriptionEn = "Complete framework for reverse engineering and analyzing binaries."
                ),
                ToolEntity(
                    id = "apktool",
                    nameAr = "أداة Apktool",
                    nameEn = "Apktool Utility",
                    category = "REVERSE",
                    installCommand = "pkg install apktool -y",
                    descriptionAr = "أداة لتفكيك وتعديل وإعادة بناء تطبيقات الأندرويد المغلقة.",
                    descriptionEn = "A tool for reverse engineering 3rd party, closed, binary Android apps."
                ),

                // Category: WEB
                ToolEntity(
                    id = "sqlmap",
                    nameAr = "إس كيو إل ماب (SQLMap)",
                    nameEn = "SQLMap Tool",
                    category = "WEB",
                    installCommand = "pkg install sqlmap -y",
                    descriptionAr = "الكشف التلقائي واستغلال ثغرات حقن قواعد البيانات (SQL Injection).",
                    descriptionEn = "Automatic SQL injection and database takeover tool."
                ),
                ToolEntity(
                    id = "dirsearch",
                    nameAr = "دير سيرش (Dirsearch)",
                    nameEn = "Dirsearch Scanner",
                    category = "WEB",
                    installCommand = "pip install dirsearch",
                    descriptionAr = "أداة ذكية وقوية للبحث عن المجلدات والملفات المخفية داخل خوادم الويب.",
                    descriptionEn = "Advanced command-line tool designed to brute force directories."
                ),
                ToolEntity(
                    id = "nikto",
                    nameAr = "نيكتو (Nikto)",
                    nameEn = "Nikto Scanner",
                    category = "WEB",
                    installCommand = "pkg install nikto -y",
                    descriptionAr = "فاحص شامل لخوادم الويب يكتشف الملفات الخطيرة والتهويدات الخاطئة.",
                    descriptionEn = "Web server scanner which performs comprehensive tests against items."
                ),

                // Category: WIRELESS
                ToolEntity(
                    id = "aircrack",
                    nameAr = "إير كراك (Aircrack-ng)",
                    nameEn = "Aircrack-ng Suite",
                    category = "WIRELESS",
                    installCommand = "pkg install aircrack-ng -y",
                    descriptionAr = "مجموعة متكاملة لتقييم وفحص واختراق شبكات الواي فاي اللاسلكية.",
                    descriptionEn = "Complete suite of tools to assess WiFi network security."
                )
            )
            toolDao.insertTools(initialList)
        }
    }

    suspend fun updateInstallStatus(toolId: String, isInstalled: Boolean) {
        val time = if (isInstalled) System.currentTimeMillis() else null
        toolDao.updateInstallStatus(toolId, isInstalled, time)
    }

    suspend fun toggleFavorite(toolId: String, currentFavorite: Boolean) {
        toolDao.updateFavoriteStatus(toolId, !currentFavorite)
    }
}
