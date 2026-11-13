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
    ),
    Lesson(
        "transmission", "Трансмиссия трактора", "Тракторы",
        "Как крутящий момент передаётся от двигателя к колёсам и ВОМ.",
        listOf(
            "Трансмиссия передаёт механическую энергию от двигателя к движителям и изменяет передаточное отношение под условия движения.",
            "Типовая цепочка механической трансмиссии: сцепление, коробка передач, главная передача, дифференциал и конечные передачи. Реальная компоновка зависит от модели.",
            "Сцепление временно разъединяет двигатель и трансмиссию. Коробка передач позволяет выбирать скорость и тяговое усилие.",
            "Дифференциал позволяет ведущим колёсам вращаться с разной частотой при повороте. Блокировку применяют только по инструкции и обычно отключают перед поворотом.",
            "Вал отбора мощности передаёт вращение рабочим органам агрегатов. Защитный кожух ВОМ должен быть установлен, а к вращающемуся валу нельзя приближаться.",
            "Гидростатические и бесступенчатые трансмиссии работают иначе, чем классические механические коробки передач."
        ), "трансмиссия сцепление коробка передач дифференциал ВОМ"
    ),
    Lesson(
        "hydraulics", "Гидравлическая система трактора", "Тракторы",
        "Как поток масла приводит в движение навесное оборудование и гидроцилиндры.",
        listOf(
            "Гидравлическая система передаёт энергию с помощью рабочей жидкости под давлением. Она может поднимать навеску, перемещать рабочие органы и приводить гидромоторы.",
            "Основные элементы: бак или общий картер, насос, фильтр, распределитель, клапаны, трубопроводы, гидроцилиндры и гидромоторы.",
            "Насос создаёт поток жидкости. Давление возникает при сопротивлении потоку и ограничивается клапанами и конструкцией системы.",
            "Распределитель направляет поток к исполнительному механизму. Гидроцилиндр преобразует энергию жидкости в поступательное движение штока.",
            "Теоретическое усилие цилиндра зависит от давления и эффективной площади поршня: F = p × A.",
            "Загрязнение масла ускоряет износ и может вызвать заедание клапанов. Тип жидкости и интервалы замены определяются изготовителем.",
            "Опасность: струя масла под высоким давлением может проникнуть под кожу. Не искать утечки рукой. Перед отсоединением рукавов сбросить давление по инструкции.",
            "Не работать под поднятым оборудованием без механических упоров. При инъекционной травме гидравлической жидкостью нужна немедленная медицинская помощь."
        ), "гидравлика насос масло давление распределитель гидроцилиндр"
    )

)


private data class CabinControl(
    val id: String,
    val title: String,
    val zone: String,
    val purpose: String,
    val action: String,
    val caution: String
)

private val cabinControls = listOf(
    CabinControl("wheel", "Рулевое колесо", "Рабочее место", "Задаёт направление движения трактора через рулевой механизм.", "Поворот рулевого колеса передаёт управляющее воздействие на рулевую систему. Усилитель снижает усилие оператора.", "Не регулировать рулевую колонку во время движения. При необычно тяжёлом рулении остановиться и проверить систему."),
    CabinControl("clutch", "Педаль сцепления", "Пол и педали", "Временно разъединяет двигатель и трансмиссию.", "При нажатии сцепление выключается; при плавном отпускании крутящий момент снова передаётся в трансмиссию.", "Не держать ногу на педали без необходимости. Конкретный порядок трогания и переключения изучать с инструктором."),
    CabinControl("brake", "Педали рабочих тормозов", "Пол и педали", "Замедляют и останавливают трактор; на исполнениях с раздельными педалями воздействуют на соответствующие стороны.", "Соединительная планка объединяет педали для совместного торможения. Перед движением по дорогам действовать по руководству машины.", "Не выполнять поворот раздельными тормозами на высокой скорости. Проверять фиксацию соединительной планки."),
    CabinControl("throttle", "Педаль подачи топлива", "Пол и педали", "Изменяет подачу топлива и частоту вращения двигателя.", "Нажатие педали увеличивает подачу топлива в пределах настройки регулятора двигателя.", "Не путать с тормозом. Не задавать обороты без учёта нагрузки и руководства."),
    CabinControl("gear", "Рычаг переключения передач", "Трансмиссия", "Выбирает передачу механической коробки передач.", "Передача определяет соотношение скорости движения и тягового усилия.", "Схема КПП зависит от модификации. Не переключать по схеме от другого исполнения."),
    CabinControl("range", "Рычаг диапазонов / редуктора", "Трансмиссия", "Выбирает дополнительный диапазон или режим трансмиссии, если он установлен.", "Изменяет доступный набор передаточных отношений.", "Наличие и схема различаются. Точное положение и порядок переключения — только по руководству конкретного трактора."),
    CabinControl("pto", "Управление ВОМ", "Правая / боковая зона", "Включает или отключает передачу вращения к сельскохозяйственному агрегату.", "ВОМ передаёт механическую мощность от двигателя к рабочему органу машины.", "Опасность захвата. Кожух должен быть установлен; не приближаться к вращающемуся валу."),
    CabinControl("hydraulic", "Рычаги гидрораспределителя", "Правая зона", "Направляют поток масла к гидроцилиндрам и механизмам.", "В зависимости от секции могут обеспечивать подъём, нейтраль, опускание или плавающее положение.", "Направление рычага и назначение секций сверять по схеме конкретной гидросистемы. Не работать под поднятой навеской."),
    CabinControl("lift", "Управление задней навеской", "Правая зона", "Управляет подъёмом и опусканием заднего навесного оборудования.", "Система использует гидравлический поток и механизм навески для изменения положения орудия.", "Оборудование может опуститься под собственной массой. Не находиться под поднятым орудием без штатной механической фиксации."),
    CabinControl("starter", "Выключатель стартера и приборов", "Передняя панель", "Включает электрические цепи и управляет запуском двигателя согласно положениями выключателя.", "Положения ключа и последовательность запуска зависят от двигателя и комплектации.", "Не запускать двигатель в закрытом помещении. Перед запуском убедиться, что рядом нет людей и органы управления находятся в безопасном положении."),
    CabinControl("ground", "Выключатель «массы»", "Боковая зона", "Подключает или отключает аккумуляторную батарею от бортовой электрической сети.", "Размыкает или соединяет цепь питания бортовой сети.", "Это не замок зажигания и не останов двигателя. Использовать в порядке, указанном изготовителем."),
    CabinControl("stop", "Рукоятка останова двигателя", "Боковая зона", "Останавливает дизельный двигатель предусмотренным конструкцией способом.", "На соответствующих исполнениях воздействует на механизм прекращения подачи топлива.", "Не путать с выключателем «массы». Конструкция останова зависит от двигателя."),
    CabinControl("cluster", "Комбинация приборов", "Передняя панель", "Позволяет контролировать основные параметры и состояние систем трактора.", "Включает приборы и сигнализаторы, состав которых зависит от установленной панели.", "При аварийном сигнале давления масла или перегрева действовать по руководству: безопасно остановить трактор и выяснить причину."),
    CabinControl("temp", "Указатель температуры охлаждающей жидкости", "Передняя панель", "Показывает тепловое состояние двигателя.", "Датчик передаёт значение температуры на указатель; отдельный сигнализатор может предупреждать о перегреве.", "Не открывать крышку горячей системы охлаждения — возможен выброс кипящей жидкости и пара."),
    CabinControl("oil", "Указатель / сигнализатор давления масла", "Передняя панель", "Контролирует давление в системе смазки двигателя.", "Показывает наличие рабочего давления или предупреждает о его опасном снижении.", "При аварийном сигнале давления масла немедленно безопасно остановить двигатель. Не продолжать работу в надежде, что сигнал исчезнет."),
    CabinControl("fuel", "Указатель уровня топлива", "Передняя панель", "Показывает количество топлива в баке.", "Датчик уровня передаёт показание на приборную панель.", "Показание ориентировочное; не допускать работы двигателя до полного опустошения топливной системы."),
    CabinControl("lights", "Переключатели света", "Передняя панель", "Управляют наружным освещением и световой сигнализацией.", "Включают предусмотренные комплектацией габаритные огни, ближний/дальний свет и рабочие фары.", "Перед дорогой проверить исправность освещения и соответствие правилам движения."),
    CabinControl("turn", "Подрулевой переключатель", "Рулевая колонка", "Управляет указателями поворота и отдельными функциями света/звукового сигнала в зависимости от исполнения.", "Переключатель замыкает соответствующие электрические цепи.", "Функции и направления переключения сверять с маркировкой конкретного переключателя."),
    CabinControl("wiper", "Стеклоочиститель и омыватель", "Верхняя / передняя панель", "Обеспечивают обзор через стёкла в дождь, снег и грязь.", "Электропривод перемещает щётку; омыватель подаёт жидкость на стекло.", "Не включать сухую щётку по загрязнённому стеклу, если это может повредить его или щётку."),
    CabinControl("heater", "Отопитель и вентиляция", "Верхняя панель", "Обеспечивают обогрев и циркуляцию воздуха в кабине.", "Кран отопителя регулирует подачу горячей охлаждающей жидкости, вентилятор перемещает воздух, дефлекторы задают направление потока.", "При запотевании стёкол обеспечить обзор до начала движения. Не перекрывать вентиляцию полностью.")
)

@Composable
private fun CabinScreen(onBack: () -> Unit) {
    var selectedId by remember { mutableStateOf("wheel") }
    var group by remember { mutableStateOf("Все") }
    val groups = listOf("Все", "Передняя панель", "Пол и педали", "Трансмиссия", "Правая зона", "Боковая зона", "Рулевая колонка", "Верхняя панель", "Рабочее место")
    val visible = cabinControls.filter { group == "Все" || it.zone == group }
    val selected = cabinControls.firstOrNull { it.id == selectedId } ?: cabinControls.first()

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("← Назад", color = accent) }
            Spacer(Modifier.weight(1f))
            Text("МТЗ-82.1", color = accent, fontWeight = FontWeight.Bold)
        }
        Text("КАБИНА И ОРГАНЫ УПРАВЛЕНИЯ", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Text("Интерактивная учебная схема", color = mainText, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Выбери зону, затем нажми на орган управления. Схема условная: расположение и комплектация сверяются с конкретным трактором.", color = muted, fontSize = 12.sp, lineHeight = 17.sp)
        Spacer(Modifier.height(12.dp))
        Surface(color = panel, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                Text("СХЕМА КАБИНЫ — ВИД ОПЕРАТОРА", color = muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Передняя панель", "Рулевая колонка").forEach { zone ->
                        Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f).clickable { group = zone; selectedId = cabinControls.first { it.zone == zone }.id }) {
                            Text(zone, color = mainText, fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
                Spacer(Modifier.height(7.dp))
                Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().clickable { group = "Рабочее место"; selectedId = "wheel" }) {
                    Text("             РУЛЕВОЕ КОЛЕСО / МЕСТО ОПЕРАТОРА", color = mainText, fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                }
                Spacer(Modifier.height(7.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f).clickable { group = "Пол и педали"; selectedId = "clutch" }) {
                        Text("ПЕДАЛИ\nСцепление • тормоза • газ", color = mainText, fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                    }
                    Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f).clickable { group = "Трансмиссия"; selectedId = "gear" }) {
                        Text("ТРАНСМИССИЯ\nПередачи • диапазоны", color = mainText, fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                    }
                }
                Spacer(Modifier.height(7.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f).clickable { group = "Боковая зона"; selectedId = "ground" }) {
                        Text("БОКОВАЯ ЗОНА\nМасса • останов", color = mainText, fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                    }
                    Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(10.dp), modifier = Modifier.weight(1f).clickable { group = "Правая зона"; selectedId = "hydraulic" }) {
                        Text("ГИДРАВЛИКА / ВОМ\nНавеска • привод", color = mainText, fontSize = 11.sp, modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text("ВЫБОР ЗОНЫ", color = muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        LazyColumn(Modifier.heightIn(max = 104.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            items(groups.chunked(2)) { pair ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    pair.forEach { item ->
                        FilterChip(selected = group == item, onClick = { group = item; visible.firstOrNull { it.zone == item }?.let { selectedId = it.id } }, label = { Text(item, fontSize = 10.sp) })
                    }
                }
            }
        }
        Text("ОРГАНЫ УПРАВЛЕНИЯ", color = muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        LazyColumn(Modifier.heightIn(max = 160.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            items(visible, key = { it.id }) { control ->
                Surface(color = if (selectedId == control.id) Color(0xFF344B38) else panel, shape = RoundedCornerShape(9.dp), modifier = Modifier.fillMaxWidth().clickable { selectedId = control.id }) {
                    Text(control.title, color = mainText, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 11.dp, vertical = 9.dp))
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Surface(color = panel, shape = RoundedCornerShape(14.dp), modifier = Modifier.weight(1f).fillMaxWidth()) {
            LazyColumn(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    Text(selected.zone.uppercase(), color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(selected.title, color = mainText, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text("НАЗНАЧЕНИЕ", color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(selected.purpose, color = mainText, fontSize = 13.sp, lineHeight = 19.sp)
                    Text("КАК РАБОТАЕТ", color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(selected.action, color = mainText, fontSize = 13.sp, lineHeight = 19.sp)
                    Text("БЕЗОПАСНОСТЬ", color = Color(0xFFE3B66D), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(selected.caution, color = mainText, fontSize = 13.sp, lineHeight = 19.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Статус: учебное описание. Перед практикой сверить орган и процедуру с руководством именно вашей модификации.", color = muted, fontSize = 11.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}

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
    var showCabin by remember { mutableStateOf(false) }
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

            if (showCabin) {\n                CabinScreen(onBack = { showCabin = false })\n            } else if (selected == null) {
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
                Surface(color = Color(0xFF26372B), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().clickable { showCabin = true }) {\n                    Column(Modifier.padding(14.dp)) {\n                        Text("ПРАКТИКУМ • МТЗ-82.1", color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)\n                        Text("Кабина и органы управления →", color = mainText, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)\n                        Text("Интерактивная схема, педали, рычаги и приборы", color = muted, fontSize = 12.sp)\n                    }\n                }\n                Spacer(Modifier.height(12.dp))\n                Text("УЧЕБНЫЕ МАТЕРИАЛЫ", color = muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.3.sp)
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
