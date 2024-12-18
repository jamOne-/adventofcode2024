fun main() {
    println(solve18a(readInputLines("18"), Vector(71, 71), bytesCount = 1024))
    println(solve18b(readInputLines("18"), Vector(71, 71)))
}

fun solve18a(lines: List<String>, size: Vector, bytesCount: Int): Int {
    val corrupted = parseInput(lines.subList(0, bytesCount)).toSet()
    return checkNotNull(findPath(corrupted, size))
}

fun solve18b(lines: List<String>, size: Vector): Point {
    val corrupted = parseInput(lines)
    var l = 0
    var r = lines.lastIndex

    while (l < r) {
        val m = (l + r) / 2
        val foundPath = findPath(corrupted.subList(0, m + 1).toSet(), size) != null

        if (foundPath) {
            l = m + 1
        } else {
            r = m
        }
    }

    return corrupted[l]
}

private fun parseInput(lines: List<String>): List<Point> = lines.map {
    val split = it.split(",")
    assert(split.size == 2)
    Point(split[0].toInt(), split[1].toInt())
}

private fun findPath(corrupted: Set<Point>, size: Vector): Int? {
    val start = Point(0, 0)
    val end = size.toPoint() + Vector(-1, -1)
    val visited = mutableSetOf(*corrupted.toTypedArray(), start)
    val dq = ArrayDeque<Pair<Point, Int>>()
    dq.addFirst(Pair(start, 0))

    while (dq.isNotEmpty()) {
        val (p, s) = dq.removeFirst()
        if (p == end) {
            return s
        }

        for (d in Direction.values()) {
            val newP = move(d, p)
            if (newP !in visited && newP.x in 0..end.x && newP.y in 0..end.y) {
                visited.add(newP)
                dq.addLast(Pair(newP, s + 1))
            }
        }
    }

    return null
}
