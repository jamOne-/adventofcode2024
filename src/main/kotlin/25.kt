fun main() {
    println(solve25a(readInputLines("25")))
}

fun solve25a(lines: List<String>): Long {
    val (keys, locks) = parseInput(lines)
    val buckets = groupLocks(locks)
    var count = 0L

    for (key in keys) {
        var fitting = locks.map { it.id }.toSet()

        for ((i, h) in key.withIndex()) {
            val fittingAtI = mutableSetOf<Int>()
            for (h2 in 1..(7 - h)) {
                fittingAtI.addAll(buckets.getOrDefault(LockBucket(i, h2), setOf()))
            }
            fitting = fitting.intersect(fittingAtI)
        }

        count += fitting.size
    }

    return count
}

private typealias Heights = List<Int>

private data class Lock(val heights: Heights, val id: Int)
private data class LockBucket(val i: Int, val height: Int)
private data class Input25(val keys: List<Heights>, val locks: List<Lock>)

private fun parseInput(lines: List<String>): Input25 {
    val keys = mutableListOf<Heights>()
    val locks = mutableListOf<Lock>()

    for ((id, block) in lines.windowed(size = 7, step = 8).withIndex()) {
        val isKey = block[0] == "#####"
        val heights = mutableListOf<Int>()

        for (x in block[0].indices) {
            var height = 0
            for (y in block.indices) {
                if (block[y][x] == '#') {
                    height += 1
                }
            }
            heights.add(height)
        }

        if (isKey) {
            keys.add(heights)
        } else {
            locks.add(Lock(heights, id))
        }
    }

    return Input25(keys, locks)
}


private fun groupLocks(locks: List<Lock>): Map<LockBucket, Set<Int>> {
    val result = mutableMapOf<LockBucket, Set<Int>>()
    for (lock in locks) {
        for ((i, h) in lock.heights.withIndex()) {
            val bucket = LockBucket(i, h)
            result[bucket] = result.getOrDefault(bucket, setOf()) + lock.id
        }
    }
    return result
}
