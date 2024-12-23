fun main() {
    println(solve23a(readInputLines("23")))
    println(solve23b(readInputLines("23")))
}

fun solve23a(lines: List<String>): Int {
    val map = parseInput(lines)
    return generateParties(map, size = 3).filter { p -> p.used.any { it.startsWith("t") } }.size
}

fun solve23b(lines: List<String>): String {
    val map = parseInput(lines)
    return bronKerbosch(map).used.sorted().joinToString(",")
}

private fun parseInput(lines: List<String>): Map<String, Set<String>> {
    val map = mutableMapOf<String, Set<String>>()
    for (line in lines) {
        val split = line.split("-")
        assert(split.size == 2)
        val x = split[0]
        val y = split[1]
        map[x] = map.getOrDefault(x, mutableSetOf()) + y
        map[y] = map.getOrDefault(y, mutableSetOf()) + x
    }
    return map
}

private fun generateParties(map: Map<String, Set<String>>, size: Int): Set<Party> {
    var parties: Set<Party> = setOf(Party(used = setOf(), unused = map.keys))
    for (i in 0 until size) {
        parties = expandParties(map, parties)
    }
    return parties
}

private fun expandParties(map: Map<String, Set<String>>, prev: Set<Party>): Set<Party> {
    val result = mutableSetOf<Party>()
    for (c in prev) {
        for (x in c.unused) {
            if (isConnectedWithAllFrom(map, x, c.used)) {
                result += Party(c.used + x, c.unused - x)
            }
        }
    }
    return result
}

private fun isConnectedWithAllFrom(map: Map<String, Set<String>>, x: String, party: Set<String>): Boolean =
    party.all { it in map[x]!! }

private data class Party(val used: Set<String>, val unused: Set<String>)

private fun bronKerbosch(map: Map<String, Set<String>>): Party {
    fun aux(r: Set<String>, pIn: Set<String>): Set<String>? {
        if (pIn.isEmpty()) {
            return r
        }

        var p = pIn
        val candidates = mutableListOf<Set<String>?>()
        for (v in p) {
            val result = aux(r + v, p.intersect(map[v]!!))
            candidates.add(result)
            p -= v
        }

        return candidates.filterNotNull().maxByOrNull { it.size }
    }

    val result = checkNotNull(aux(setOf(), map.keys))
    return Party(used = result, unused = map.keys - result)
}
