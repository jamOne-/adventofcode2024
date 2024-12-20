import java.util.PriorityQueue

fun main() {
    println(solve16a(readInputLines("16")))
    println(solve16b(readInputLines("16")))
}

private data class PQEntry(val score: Int, val p: Point, val d: Direction)

fun solve16a(lines: List<String>): Int {
    val (maze, start, end) = parseInput(lines)

    val visited = mutableSetOf(Pair(start, Direction.RIGHT))
    val pq = PriorityQueue<PQEntry>(compareBy { it.score })
    pq.add(PQEntry(0, start, Direction.RIGHT))

    while (pq.isNotEmpty()) {
        val (score, p, d) = pq.remove()

        if (p == end) {
            return score
        }

        val candidates = listOf(
            PQEntry(score + 1, move(d, p), d),
            PQEntry(score + 1000, p, rotateClockwise(d)),
            PQEntry(score + 1000, p, rotateCounterclockwise(d))
        )

        for (c in candidates) {
            val cp = Pair(c.p, c.d)
            if (maze[c.p] is MazeEmpty && cp !in visited) {
                pq.add(c)
                visited.add(cp)
            }
        }
    }

    throw IllegalStateException("No path found")
}

fun solve16b(lines: List<String>): Int {
    val (maze, start, end) = parseInput(lines)

    val visited = mutableSetOf(Pair(start, Direction.RIGHT))
    val pq = PriorityQueue<PQEntry>(compareBy { it.score })
    pq.add(PQEntry(0, start, Direction.RIGHT))
    val scores = mutableMapOf(Pair(Pair(start, Direction.RIGHT), 0))
    var bestScore = Int.MAX_VALUE

    while (pq.isNotEmpty() && pq.peek().score <= bestScore) {
        val (score, p, d) = pq.remove()
        scores[Pair(p, d)] = score

        if (p == end) {
            bestScore = score
        }

        val candidates = listOf(
            PQEntry(score + 1, move(d, p), d),
            PQEntry(score + 1000, p, rotateClockwise(d)),
            PQEntry(score + 1000, p, rotateCounterclockwise(d))
        )

        for (c in candidates) {
            val cp = Pair(c.p, c.d)
            if (maze[c.p] is MazeEmpty && cp !in visited) {
                pq.add(c)
                visited.add(cp)
            }
        }
    }

    val ends = Direction.values().map { Pair(end, it) }.filter { scores.getOrDefault(it, Int.MAX_VALUE) == bestScore }
    val visitedBackwards = mutableSetOf(*ends.toTypedArray())
    val dq = ArrayDeque(ends)

    while (dq.isNotEmpty()) {
        val (p, d) = dq.removeFirst()
        val s = checkNotNull(scores[Pair(p, d)])

        val candidates = listOf(
            PQEntry(s - 1, moveBackwards(d, p), d),
            PQEntry(s - 1000, p, rotateClockwise(d)),
            PQEntry(s - 1000, p, rotateCounterclockwise(d))
        )

        for (c in candidates) {
            val cp = Pair(c.p, c.d)
            if (c.score == scores[cp] && cp !in visitedBackwards) {
                visitedBackwards.add(cp)
                dq.addFirst(cp)
            }
        }
    }

    return visitedBackwards.map { it.first }.toSet().size
}

private data class Input16(val maze: Map<Point, MazeField>, val start: Point, val end: Point)

private fun parseInput(lines: List<String>): Input16 {
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

    return Input16(map, checkNotNull(start), checkNotNull(end))
}
