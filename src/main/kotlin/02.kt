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

    return reports.map(::generateReportsWithoutAtMostOneLevel).filter { it.any(::isReportSafe) }.size
}

private fun generateReportsWithoutAtMostOneLevel(report: List<Int>): List<List<Int>> =
    report.indices.map { i -> report.filterIndexed { j, _ -> i != j } } + listOf(report)

private fun isReportSafe(report: List<Int>): Boolean {
    assert(report.size > 2)
    val increasing = report[0] < report[1]
    val multiplier = if (increasing) 1 else -1

    for (i in 0..report.size - 2) {
        val diff = (report[i + 1] - report[i]) * multiplier

        if (diff <= 0 || diff > 3) {
            return false
        }
    }

    return true
}