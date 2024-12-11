fun main() {
    println(solve11a(readInputLines("11")))
    println(solve11b(readInputLines("11")))
}

fun solve11a(lines: List<String>): Long {
    assert(lines.size == 1)
    val stones = lines[0].split(' ').map { it.toLong() }
    return stones.sumOf { countStones(it, 25) }
}

fun solve11b(lines: List<String>): Long {
    assert(lines.size == 1)
    val stones = lines[0].split(' ').map { it.toLong() }
    return stones.sumOf { countStones(it, 75) }
}

private val mem = mutableMapOf<Pair<Long, Int>, Long>()
private fun countStones(id: Long, blinks: Int): Long {
    val p = Pair(id, blinks)
    if (p in mem) {
        return mem[p]!!
    }

    val result = when {
        blinks == 0 -> 1
        id == 0L -> countStones(1, blinks - 1)
        id.toString().length % 2 == 0 -> {
            val idString = id.toString()
            val id1 = idString.substring(0, idString.length / 2).toLong()
            val id2 = idString.substring(idString.length / 2).toLong()
            countStones(id1, blinks - 1) + countStones(id2, blinks - 1)
        }

        else -> countStones(id * 2024, blinks - 1)
    }

    mem[p] = result
    return result
}
