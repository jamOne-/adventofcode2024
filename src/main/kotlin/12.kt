import java.util.ArrayDeque

fun main() {
    println(solve12a(readInputLines("12")))
    println(solve12b(readInputLines("12")))
}

fun solve12a(lines: List<String>): Int {
    val (map, size) = parseInput(lines)
    val divided = divideByAreas(map, size)
    return divided.sumOf { (_, area) -> area.size * calculatePerimeter(map, area) }
}

fun solve12b(lines: List<String>): Int {
    val (map, size) = parseInput(lines)
    val divided = divideByAreas(map, size)
    return divided.sumOf { (_, area) -> area.size * calculateSides(area) }
}

private data class Input12(val map: Map<Point, Char>, val size: Vector)

private fun parseInput(lines: List<String>): Input12 {
    val map = mutableMapOf<Point, Char>()
    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            map[Point(x, y)] = c
        }
    }
    return Input12(map, Vector(lines.first().length, lines.size))
}

private fun divideByAreas(map: Map<Point, Char>, size: Vector): List<Pair<Char, Set<Point>>> {
    val result = mutableListOf<Pair<Char, Set<Point>>>()
    val visited = mutableSetOf<Point>()

    for (y in 0 until size.y) {
        for (x in 0 until size.x) {
            if (Point(x, y) in visited) {
                continue
            }

            val start = Point(x, y)
            val dq = ArrayDeque<Point>()
            val currentVisited = mutableSetOf<Point>()
            dq.addFirst(start)
            currentVisited.add(start)

            while (dq.isNotEmpty()) {
                val p = dq.removeFirst()

                for (d in Direction.values()) {
                    val newPoint = move(d, p)

                    if (!currentVisited.contains(newPoint) && map[newPoint] == map[start]) {
                        currentVisited.add(newPoint)
                        dq.addFirst(newPoint)
                    }
                }
            }

            visited.addAll(currentVisited)
            result.add(Pair(map[start]!!, currentVisited))
        }
    }

    return result
}

private fun calculatePerimeter(map: Map<Point, Char>, area: Set<Point>): Int =
    area.sumOf { p -> Direction.values().filter { map[move(it, p)] != map[p] }.size }

private data class Edge(val field: Point, val direction: Direction)

private fun calculateSides(area: Set<Point>): Int {
    val edges = area.flatMap { p -> Direction.values().filter { !area.contains(move(it, p)) }.map { Edge(p, it) } }
    val (vertical, horizontal) = edges.partition { it.direction == Direction.RIGHT || it.direction == Direction.LEFT }
    val verticalGrouped = vertical.groupBy { Pair(it.direction, it.field.x) }
    val horizontalGrouped = horizontal.groupBy { Pair(it.direction, it.field.y) }

    fun calculateGaps(xs: List<Int>): Int = xs.zipWithNext().sumOf { (a, b) -> if (b - a > 1) 1.toInt() else 0 } + 1

    val verticalSum = verticalGrouped.values.map { it.map { it.field.y }.sorted() }.sumOf { calculateGaps(it) }
    val horizontalSum = horizontalGrouped.values.map { it.map { it.field.x }.sorted() }.sumOf { calculateGaps(it) }

    return verticalSum + horizontalSum
}
