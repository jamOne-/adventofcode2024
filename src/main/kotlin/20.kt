fun main() {
    println(solve20a(readInputLines("20")))
    println(solve20b(readInputLines("20")))
}

fun solve20a(lines: List<String>): Int = solve20ForDistance(lines, cheatingDistance = 2)
fun solve20b(lines: List<String>): Int = solve20ForDistance(lines, cheatingDistance = 20)

private fun solve20ForDistance(lines: List<String>, cheatingDistance: Int): Int {
    val (maze, start, end) = parseInput(lines)

    val scoresFromStart = getDistancesFromPoint(maze, start)
    val scoresFromEnd = getDistancesFromPoint(maze, end)
    val withoutCheating = checkNotNull(scoresFromStart[end])

    var count = 0
    for ((p, _) in maze.entries.filter {
        it.value is MazeEmpty && scoresFromStart.getOrDefault(
            it.key, Int.MAX_VALUE
        ) < withoutCheating
    }) {
        val fromStart = checkNotNull(scoresFromStart[p])
        val availableFields =
            getPointsWithinDistance(maze, p, cheatingDistance).filter { maze[it] is MazeEmpty && it in scoresFromEnd }

        for (p2 in availableFields) {
            val total = fromStart + p2.distance(p) + checkNotNull(scoresFromEnd[p2])

            if (withoutCheating - total >= 100) {
                count += 1
            }
        }
    }

    return count
}

private data class Input20(val maze: Map<Point, MazeField>, val start: Point, val end: Point)

private fun parseInput(lines: List<String>): Input20 {
    val map = mutableMapOf<Point, MazeField>()
    var start: Point? = null
    var end: Point? = null

    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            val p = Point(x, y)
            if (c == 'E') {
                end = p
            } else if (c == 'S') {
                start = p
            }

            val field = if (c == '#') MazeWall else MazeEmpty
            map[p] = field
        }
    }

    return Input20(map, checkNotNull(start), checkNotNull(end))
}

private fun getDistancesFromPoint(maze: Map<Point, MazeField>, start: Point): Map<Point, Int> {
    val visited = mutableSetOf(start)
    val dq = ArrayDeque<Pair<Point, Int>>()
    dq.addLast(Pair(start, 0))
    val scores = mutableMapOf(Pair(start, 0))

    while (dq.isNotEmpty()) {
        val (p, s) = dq.removeFirst()
        scores[p] = s

        for (d in Direction.values()) {
            val p2 = move(d, p)
            if (maze[p2] is MazeEmpty && p2 !in visited) {
                visited.add(p2)
                dq.addLast(Pair(p2, s + 1))
            }
        }
    }

    return scores
}

fun getPointsWithinDistance(maze: Map<Point, MazeField>, start: Point, distance: Int): Set<Point> {
    val visited = mutableSetOf(start)
    val dq = ArrayDeque<Pair<Point, Int>>()
    dq.addLast(Pair(start, 0))

    while (dq.isNotEmpty()) {
        val (p, s) = dq.removeFirst()

        if (s == distance) {
            continue
        }

        for (d in Direction.values()) {
            val p2 = move(d, p)
            if (p2 !in visited && p2 in maze) {
                visited.add(p2)
                dq.addLast(Pair(p2, s + 1))
            }
        }
    }

    return visited - start
}
