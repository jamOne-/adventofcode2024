import kotlin.math.sign

fun main() {
    println(solve02a(readInputLines("02")))
    println(solve02b(readInputLines("02")))
}

fun solve02a(lines: List<String>): Int {
    val reports = lines.map { line -> line.split(" ").map { it.toInt() } }

    return reports.filter(::isReportSafe).size
}

fun solve02b(lines: List<String>): Int {
    val reports = lines.map { line -> line.split(" ").map { it.toInt() } }

    return reports.filter(::isReportAlmostSafe).size
}

private fun isReportSafe(report: List<Int>): Boolean {
    assert(report.size >= 2)
    val increasing = report[0] < report[1]
    val multiplier = if (increasing) 1 else -1

    for (i in 1..report.lastIndex) {
        if (!isDifferenceSafe(report[i - 1], report[i], multiplier)) {
            return false
        }
    }

    return true
}

private fun isReportAlmostSafe(report: List<Int>): Boolean {
    assert(report.size >= 3)
    val multiplier = getMonotonicityMultiplier(report)
    if (multiplier == 0) {
        return false
    }

    var notUsedWithCurrent = true
    var usedWithoutCurrent = true
    var usedWithCurrent = true

    for (i in 1..report.lastIndex) {
        if (!notUsedWithCurrent && !usedWithoutCurrent && !usedWithCurrent) {
            return false
        }

        val newNotUsedWithCurrent = notUsedWithCurrent && isDifferenceSafe(report[i - 1], report[i], multiplier)
        val newUsedWithoutCurrent = notUsedWithCurrent
        val newUsedWithCurrent = i == 1 || (usedWithCurrent && isDifferenceSafe(
            report[i - 1],
            report[i],
            multiplier
        )) || (usedWithoutCurrent && isDifferenceSafe(report[i - 2], report[i], multiplier))

        notUsedWithCurrent = newNotUsedWithCurrent
        usedWithoutCurrent = newUsedWithoutCurrent
        usedWithCurrent = newUsedWithCurrent
    }

    return notUsedWithCurrent || usedWithoutCurrent || usedWithCurrent
}

private fun getMonotonicityMultiplier(report: List<Int>): Int {
    val (a, b, c, d) = listOf(report[0], report[1], report[2], report[3])
    return listOf(b - a, c - a, d - a, c - b, d - b, d - c).map { it.sign }.sorted()[3]
}

private fun isDifferenceSafe(x: Int, y: Int, monotonicity: Int): Boolean = ((y - x) * monotonicity) in 1..3
