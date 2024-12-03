fun main() {
    println(solve03a(readInputLines("03")))
    println(solve03b(readInputLines("03")))
}

fun solve03a(lines: List<String>): Int = parseLine(lines.joinToString()).sumOf { it.x * it.y }

fun solve03b(lines: List<String>): Int = parseLine(prepareLine(lines.joinToString())).sumOf { it.x * it.y }

private data class Multiply(val x: Int, val y: Int)

private val MULTIPLY_REGEX = Regex("""mul\((\d+),(\d+)\)""")
private fun parseLine(line: String): List<Multiply> =
    MULTIPLY_REGEX.findAll(line).map { Multiply(it.groupValues[1].toInt(), it.groupValues[2].toInt()) }.toList()

private val DONT_DO_REPLACE_REGEX = Regex("""don't\(\).*?do\(\)""")
private val DONT_ANYTHING_REPLACE_REGEX = Regex("""don't\(\).*""")
private fun prepareLine(line: String): String =
    line.replace(DONT_DO_REPLACE_REGEX, "").replace(DONT_ANYTHING_REPLACE_REGEX, "")