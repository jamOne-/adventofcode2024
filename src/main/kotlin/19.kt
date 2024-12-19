fun main() {
    println(solve19a(readInputLines("19")))
    println(solve19b(readInputLines("19")))
}

fun solve19a(lines: List<String>): Int {
    val (designs, towels) = parseInput(lines)
    return towels.count { countPossibleOrders(designs, it) > 0 }
}

fun solve19b(lines: List<String>): Long {
    val (designs, towels) = parseInput(lines)
    return towels.sumOf { countPossibleOrders(designs, it) }
}

private data class Input19(val designs: List<String>, val towels: List<String>)

private fun parseInput(lines: List<String>): Input19 {
    val designs = lines[0].split(", ")
    val towels = lines.drop(2)
    return Input19(designs, towels)
}

private val MEM = mutableMapOf<String, Long>()
private fun countPossibleOrders(designs: List<String>, towels: String): Long {
    if (towels.isEmpty()) {
        return 1
    }
    if (towels !in MEM) {
        var result = 0L
        for (d in designs) {
            if (towels.startsWith(d)) {
                result += countPossibleOrders(designs, towels.removePrefix(d))
            }
        }
        MEM[towels] = result
    }

    return checkNotNull(MEM[towels])
}
