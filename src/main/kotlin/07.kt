import java.math.BigInteger

fun main() {
    println(solve07a(readInputLines("07")))
    println(solve07b(readInputLines("07")))
}

fun solve07a(lines: List<String>): BigInteger = parseInput(lines).filter(::isCorrect).sumOf { it.total }

fun solve07b(lines: List<String>): BigInteger =
    parseInput(lines).filter { isCorrect(it, part2 = true) }.sumOf { it.total }

val LINE_REGEX = Regex("""^(\d+): (.+)$""")

private data class Line(val total: BigInteger, val xs: List<BigInteger>)

private fun parseInput(lines: List<String>): List<Line> = lines.map { line ->
    val match = LINE_REGEX.find(line)
    checkNotNull(match)
    Line(match.groupValues[1].toBigInteger(), match.groupValues[2].split(' ').map { it.toBigInteger() })
}

private fun isCorrect(line: Line, part2: Boolean = false): Boolean {
    val mem = mutableMapOf<Pair<BigInteger, Int>, Boolean>()

    fun tmp(total: BigInteger, i: Int): Boolean {
        if (Pair(total, i) in mem) {
            return checkNotNull(mem[Pair(total, i)])
        }

        val result = if (i == 0) {
            total == line.xs[i]
        } else {
            val split = split(total, line.xs[i])
            val canSubtract = total >= line.xs[i]
            val canDivide = total % line.xs[i]
            val canSplit = part2 && split != null
            canSubtract && tmp(total - line.xs[i], i - 1) || canDivide == BigInteger.valueOf(0) && tmp(
                total / line.xs[i], i - 1
            ) || canSplit && tmp(split!!, i - 1)
        }

        mem[Pair(total, i)] = result
        return result
    }

    return tmp(line.total, line.xs.lastIndex)
}

private fun split(a: BigInteger, b: BigInteger): BigInteger? {
    val aStr = a.toString()
    val bStr = b.toString()

    return if (aStr != bStr && aStr.endsWith(bStr)) {
        aStr.removeSuffix(bStr).toBigInteger()
    } else {
        null
    }
}