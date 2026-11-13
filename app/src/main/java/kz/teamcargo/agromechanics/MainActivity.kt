package kz.teamcargo.agromechanics

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Lesson(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val body: List<String>,
    val tags: String
)

private val lessons = listOf(
    Lesson(
        "tractor", "Общее устройство трактора", "Тракторы",
        "Основные системы машины и передача энергии от двигателя к движителям.",
        listOf(
            "Трактор — самоходная машина, предназначенная для создания тягового усилия и привода сельскохозяйственных машин.",
            "Основные системы: двигатель, трансмиссия, ходовая часть, рулевое управление, тормоза, гидравлика, навесная система, вал отбора мощности и электрооборудование.",
            "Двигатель преобразует энергию топлива в механическую работу. Трансмиссия передаёт и преобразует крутящий момент. Движители создают тяговое усилие на грунте.",
            "ВОМ передаёт вращение рабочим органам агрегатов. Гидравлика управляет навесным оборудованием и другими механизмами.",
            "Конструкция, параметры, масла и интервалы обслуживания зависят от модели. Для конкретной машины необходимо руководство изготовителя.",
            "Безопасность: не приближаться к вращающемуся ВОМ, не работать под поднятым оборудованием без механической фиксации, перед обслуживанием исключить случайный запуск."
        ), "трактор двигатель трансмиссия гидравлика ВОМ"
    ),
    Lesson(
        "combine", "Общее устройство зерноуборочного комбайна", "Комбайны",
        "Путь растительной массы: от срезания до очистки и выгрузки зерна.",
        listOf(
            "Зерноуборочный комбайн срезает растения, отделяет зерно от колосьев и соломы, очищает его от примесей и направляет в бункер.",
            "Жатка срезает растения и подаёт массу к наклонной камере. Наклонная камера транспортирует её к молотильному аппарату.",
            "Молотильный аппарат отделяет зерно от колосьев. Затем сепарирующая система выделяет оставшееся зерно из соломы.",
            "Очистка использует воздушный поток и решёта для удаления лёгких и крупных примесей. Очищенное зерно поступает в бункер.",
            "Классические комбайны часто используют барабан и подбарабанье с клавишным соломотрясом. Роторные и гибридные машины устроены иначе.",
            "Настройки зазоров, частоты вращения и воздушного потока зависят от модели, культуры и условий уборки. Их проверяют по руководству оператора.",
            "Перед очисткой или ремонтом остановить машину, отключить приводы и исключить случайный запуск. Не очищать механизмы на ходу."
        ), "комбайн жатка молотьба сепарация очистка зерно"
    ),
    Lesson(
        "diesel", "Дизельный двигатель и четырёхтактный цикл", "Двигатели",
        "Как поршень, шатун и коленчатый вал преобразуют энергию топлива в вращение.",
        listOf(
            "Дизельный двигатель — двигатель внутреннего сгорания, в котором топливо воспламеняется при впрыске в горячий воздух, сжатый в цилиндре.",
            "Основные детали: блок цилиндров, поршни, кольца, шатуны, коленчатый вал, головка блока, клапаны и механизм газораспределения.",
            "Впуск: поршень движется вниз, в цилиндр поступает воздух. Сжатие: поршень движется вверх, воздух нагревается.",
            "Рабочий ход: в горячий воздух впрыскивается топливо, оно самовоспламеняется, давление газов толкает поршень вниз.",
            "Выпуск: поршень движется вверх и вытесняет отработавшие газы. Полный четырёхтактный цикл занимает два оборота коленчатого вала.",
            "Система смазки снижает трение и износ. Система охлаждения отводит лишнее тепло. Топливная система дозирует и впрыскивает топливо.",
            "Свеча накаливания, если предусмотрена, помогает холодному запуску. Она не создаёт искру, как свеча зажигания бензинового двигателя."
        ), "дизель двигатель поршень цилиндр впуск сжатие рабочий ход выпуск"
    )
)

private val bg = Color(0xFF101713)
private val panel = Color(0xFF1A251E)
private val accent = Color(0xFFB8D68A)
private val muted = Color(0xFF9EAA9F)
private val mainText = Color(0xFFE9EFE8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("learning", Context.MODE_PRIVATE)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = bg, surface = panel, primary = accent,
                    onBackground = mainText, onSurface = mainText,
                    onPrimary = Color(0xFF172014)
                )
            ) {
                AgroApp(prefs)
            }
        }
    }
}

@Composable
private fun AgroApp(prefs: android.content.SharedPreferences) {
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Lesson?>(null) }
    var showSaved by remember { mutableStateOf(false) }
    var bookmarks by remember { mutableStateOf(prefs.getStringSet("bookmarks", emptySet())?.toSet() ?: emptySet()) }
    var completed by remember { mutableStateOf(prefs.getStringSet("completed", emptySet())?.toSet() ?: emptySet()) }

    Surface(modifier = Modifier.fillMaxSize(), color = bg) {
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("AGROMECHANICS", color = accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("Машиноведение", color = mainText, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                }
                Text("OFFLINE", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))

            if (selected == null) {
                Surface(color = panel, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                    BasicTextField(
                        value = query,
                        onValueChange = { query = it },
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(color = mainText, fontSize = 16.sp),
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        decorationBox = { inner ->
                            if (query.isEmpty()) Text("Поиск по темам и терминам", color = muted)
                            inner()
                        }
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = !showSaved, onClick = { showSaved = false }, label = { Text("Все темы") })
                    FilterChip(selected = showSaved, onClick = { showSaved = true }, label = { Text("Избранное") })
                }
                Spacer(Modifier.height(10.dp))
                Text("УЧЕБНЫЕ МАТЕРИАЛЫ", color = muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
                Spacer(Modifier.height(8.dp))
                val filtered = lessons.filter { lesson ->
                    val matchesQuery = query.isBlank() ||
                        (lesson.title + " " + lesson.category + " " + lesson.summary + " " + lesson.tags + " " + lesson.body.joinToString(" "))
                            .contains(query, ignoreCase = true)
                    matchesQuery && (!showSaved || bookmarks.contains(lesson.id))
                }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(filtered, key = { it.id }) { lesson ->
                        Surface(
                            color = panel, shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.fillMaxWidth().clickable { selected = lesson }
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(lesson.category.uppercase(), color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Spacer(Modifier.height(5.dp))
                                Text(lesson.title, color = mainText, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(Modifier.height(5.dp))
                                Text(lesson.summary, color = muted, fontSize = 13.sp, lineHeight = 18.sp)
                                Spacer(Modifier.height(10.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(if (completed.contains(lesson.id)) "Изучено" else "Открыть урок", color = accent, fontSize = 12.sp)
                                    Spacer(Modifier.weight(1f))
                                    Text(if (bookmarks.contains(lesson.id)) "★" else "☆", color = accent, fontSize = 20.sp)
                                }
                            }
                        }
                    }
                    if (filtered.isEmpty()) item { Text("Ничего не найдено", color = muted, modifier = Modifier.padding(12.dp)) }
                }
                Spacer(Modifier.height(8.dp))
                Text("Материалы хранятся на устройстве. Интернет и ИИ не требуются.", color = muted, fontSize = 11.sp)
            } else {
                val lesson = selected!!
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { selected = null }) { Text("← Назад", color = accent) }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = {
                        bookmarks = if (bookmarks.contains(lesson.id)) bookmarks - lesson.id else bookmarks + lesson.id
                        prefs.edit().putStringSet("bookmarks", bookmarks).apply()
                    }) { Text(if (bookmarks.contains(lesson.id)) "★ В избранном" else "☆ В избранное", color = accent) }
                }
                Text(lesson.category.uppercase(), color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(lesson.title, color = mainText, fontSize = 24.sp, fontWeight = FontWeight.Bold, lineHeight = 29.sp)
                Spacer(Modifier.height(8.dp))
                LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(lesson.body) { paragraph ->
                        Surface(color = panel, shape = RoundedCornerShape(12.dp)) {
                            Text(paragraph, color = mainText, fontSize = 15.sp, lineHeight = 23.sp, modifier = Modifier.padding(14.dp))
                        }
                    }
                }
                Button(
                    onClick = {
                        completed = completed + lesson.id
                        prefs.edit().putStringSet("completed", completed).apply()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Color(0xFF172014))
                ) {
                    Text(if (completed.contains(lesson.id)) "Урок отмечен как изученный" else "Отметить как изученный")
                }
            }
        }
    }
}
