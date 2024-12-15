fun main() {
    println(solve14a(readInputLines("14"), Vector(101, 103)))
    println(solve14b(readInputLines("14"), Vector(101, 103)))
}

fun solve14a(lines: List<String>, size: Vector): Long {
    val robots = parseInput(lines)
    val updated = update(robots, size, ticks = 100)
    return updated.map { determineQuadrant(it.p, size) }.filter { it != -1 }.groupBy { it }.values.map { it.size }
        .fold(1L) { result, x -> result * x.toLong() }
}

fun solve14b(lines: List<String>, size: Vector): Int {
    val robots = parseInput(lines)
    var current = robots
    var ticks = 0

    while (current.distinctBy { it.p }.size < current.size) {
        current = update(current, size)
        ticks += 1
    }

    draw(current, size)
    return ticks
}

private data class Robot(val p: Point, val v: Vector)

private val INPUT_LINE_14_REGEX = Regex("""^p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)$""")
private fun parseInput(lines: List<String>): List<Robot> = lines.map {
    val match = checkNotNull(INPUT_LINE_14_REGEX.find(it))
    Robot(
        Point(match.groupValues[1].toInt(), match.groupValues[2].toInt()),
        Vector(match.groupValues[3].toInt(), match.groupValues[4].toInt())
    )
}

private fun update(robots: List<Robot>, size: Vector, ticks: Int = 1): List<Robot> = robots.map {
    it.copy(
        p = (it.p + it.v * ticks) % size
    )
}

private fun determineQuadrant(p: Point, size: Vector): Int {
    val pivotX = if (size.x % 2 == 0) size.x / 2 else size.x / 2 + 1
    val pivotY = if (size.x % 2 == 0) size.y / 2 else size.y / 2 + 1

    if (size.x % 2 == 1 && p.x == pivotX - 1 || size.y % 2 == 1 && p.y == pivotY - 1) {
        return -1
    }

    return p.x / pivotX + 2 * (p.y / pivotY)
}

private fun draw(robots: List<Robot>, size: Vector) {
    val robotsMap = robots.associateBy { it.p }

    for (y in 0 until size.y) {
        for (x in 0 until size.x) {
            if (Point(x, y) in robotsMap) {
                print('#')
            } else {
                print('.')
            }
        }
        print('\n')
    }
}
