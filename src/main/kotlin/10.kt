fun main() {
    println(solve10a(readInputLines("10")))
    println(solve10b(readInputLines("10")))
}

fun solve10a(lines: List<String>): Int {
    val map = parseInput(lines)
    val startingPoints = map.filter { it.value == 0 }.keys

    return startingPoints.sumOf { possibleHikes(map, it) }
}

fun solve10b(lines: List<String>): Int {
    val map = parseInput(lines)
    val startingPoints = map.filter { it.value == 0 }.keys

    return startingPoints.sumOf { possibleHikes(map, it, part2 = true) }
}

private fun parseInput(lines: List<String>): Map<Point, Int> {
    val map = mutableMapOf<Point, Int>()
    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            map[Point(x, y)] = c.digitToInt()
        }
    }
    return map
}

private fun possibleHikes(map: Map<Point, Int>, start: Point, part2: Boolean = false): Int {
    var total = 0
    val visited = mutableSetOf(start)
    val dq = ArrayDeque<Point>()
    dq.add(0, start)

    while (dq.isNotEmpty()) {
        val current = dq.removeAt(0)

        if (map[current] == 9) {
            total += 1
            continue
        }

        for (d in Direction.values()) {
            val newPoint = move(d, current)
            if (map[newPoint] == map[current]!! + 1 && (part2 || !visited.contains(newPoint))) {
                dq.add(0, newPoint)
                visited.add(newPoint)
            }
        }
    }

    return total
}