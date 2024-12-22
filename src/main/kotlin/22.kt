fun main() {
    println(solve22a(readInputLines("22")))
    println(solve22b(readInputLines("22")))
}

fun solve22a(lines: List<String>): Long = lines.map { it.toLong() }.sumOf { getSecretNumbers(it, n = 2000).last() }

fun solve22b(lines: List<String>): Long {
    val buyersSecrets = lines.map { getSecretNumbers(it.toLong(), n = 2000) }
    val buyersLastDigits = buyersSecrets.map { secrets -> secrets.map { (it % 10).toInt() } }
    val buyersChanges = buyersLastDigits.map { ds -> ds.zipWithNext().map { (a, b) -> b - a } }
    val changesMap = mutableMapOf<List<Int>, Long>()

    for ((bi, changes) in buyersChanges.withIndex()) {
        val buyer = mutableSetOf<List<Int>>()

        for ((i, c) in changes.windowed(size = 4, step = 1).withIndex()) {
            if (c !in buyer) {
                buyer.add(c)
                changesMap[c] = changesMap.getOrDefault(c, 0) + buyersLastDigits[bi][i + 4]
            }
        }
    }

    return changesMap.values.max()
}

private const val PRUNE_BY = 16777216L
private fun nextNumber(secret: Long): Long {
    var n = secret.shl(6).xor(secret).mod(PRUNE_BY)
    n = n.shr(5).xor(n).mod(PRUNE_BY)
    n = n.shl(11).xor(n).mod(PRUNE_BY)

    return n
}

private fun getSecretNumbers(secret: Long, n: Int): List<Long> =
    (0 until n).runningFold(secret) { s, _ -> nextNumber(s) }
