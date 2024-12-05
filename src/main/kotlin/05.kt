fun main() {
    println(solve05a(readInputLines("05")))
    println(solve05b(readInputLines("05")))
}

fun solve05a(lines: List<String>): Int {
    val (ordering, updates) = parseInput(lines)
    return updates.filter { isUpdateCorrect(ordering, it) }.sumOf { it[it.size / 2] }
}

fun solve05b(lines: List<String>): Int {
    val (ordering, updates) = parseInput(lines)
    val incorrectUpdates = updates.filter { !isUpdateCorrect(ordering, it) }
    return incorrectUpdates.map { fixUpdate(ordering, it) }.sumOf { it[it.size / 2] }
}

private fun isUpdateCorrect(ordering: Set<Pair<Int, Int>>, update: List<Int>): Boolean {
    for ((i, a) in update.withIndex()) {
        for (b in update.drop(i + 1)) {
            if (Pair(b, a) in ordering) {
                return false
            }
        }
    }

    return true
}

private fun fixUpdate(ordering: Set<Pair<Int, Int>>, update: List<Int>): List<Int> {
    val fixed: MutableList<Int> = update.toMutableList()

    for (i in 1..fixed.size) {
        for (j in 0 until fixed.size - i) {
            if (Pair(fixed[j + 1], fixed[j]) in ordering) {
                val tmp = fixed[j]
                fixed[j] = fixed[j + 1]
                fixed[j + 1] = tmp
            }
        }
    }

    return fixed
}

private val ORDER_REGEX = Regex("""(\d+)\|(\d+)""")

private data class Input(val ordering: Set<Pair<Int, Int>>, val updates: List<List<Int>>)

private fun parseInput(lines: List<String>): Input {
    val ordering: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val updates: MutableList<List<Int>> = mutableListOf()

    for (line in lines) {
        val orderMatch = ORDER_REGEX.find(line)

        if (orderMatch == null) {
            if (line.isEmpty()) {
                continue
            }

            val numbers = line.split(",").map { it.toInt() }
            updates.add(numbers)
        } else {
            ordering.add(Pair(orderMatch.groupValues[1].toInt(), orderMatch.groupValues[2].toInt()))
        }
    }

    return Input(ordering, updates)
}