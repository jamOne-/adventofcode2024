fun main() {
    println(solve08a(readInputLines("08")))
    println(solve08b(readInputLines("08")))
}

fun solve08a(lines: List<String>): Int {
    val (size, antennas) = parseInput(lines)
    return antennas.values.flatMap { getAntinodeLocations(size, it) }.toSet().size
}

fun solve08b(lines: List<String>): Int {
    val (size, antennas) = parseInput(lines)
    return antennas.values.flatMap { getAntinodeLocations2(size, it) }.toSet().size
}

private data class City(val size: Vector, val antennas: Map<Char, Set<Point>>)

private fun parseInput(lines: List<String>): City {
    val antennas = mutableMapOf<Char, Set<Point>>()

    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            if (c == '.') {
                continue
            }

            val existing = antennas.getOrDefault(c, setOf())
            antennas[c] = existing + setOf(Point(x, y))
        }
    }

    return City(Vector(lines[0].length, lines.size), antennas)
}

private fun getAntinodeLocations(size: Vector, antennas: Set<Point>): Set<Point> {
    val result = mutableSetOf<Point>()
    val list = antennas.toList()

    for (i in antennas.indices) {
        for (j in i + 1 until antennas.size) {
            val delta = Vector(list[j].x - list[i].x, list[j].y - list[i].y)
            result.add(Point(list[i].x - delta.x, list[i].y - delta.y))
            result.add(Point(list[j].x + delta.x, list[j].y + delta.y))
        }
    }

    return result.filter { isPointInBounds(size, it) }.toSet()
}

private fun getAntinodeLocations2(size: Vector, antennas: Set<Point>): Set<Point> {
    val result = mutableSetOf<Point>()
    val list = antennas.toList()

    for (i in antennas.indices) {
        for (j in i + 1 until antennas.size) {
            result.addAll(getPointsInLine(list[i], list[j], size))
        }
    }

    return result
}

private fun getPointsInLine(antenna1: Point, antenna2: Point, size: Vector): Set<Point> {
    val delta = simplifyDelta(Vector(antenna2.x - antenna1.x, antenna2.y - antenna1.y))
    val result = mutableSetOf<Point>()

    var current = antenna1
    while (isPointInBounds(size, current)) {
        result.add(current)
        current = Point(current.x - delta.x, current.y - delta.y)
    }
    current = antenna1
    while (isPointInBounds(size, current)) {
        result.add(current)
        current = Point(current.x + delta.x, current.y + delta.y)
    }

    return result
}

private fun simplifyDelta(delta: Vector): Vector =
    when {
        delta.x == 0 -> Vector(0, 1)
        delta.y == 0 -> Vector(1, 0)
        else -> {
            val gcd = delta.x.toBigInteger().gcd(delta.y.toBigInteger()).toInt()
            Vector(delta.x / gcd, delta.y / gcd)
        }
    }

private fun isPointInBounds(size: Vector, point: Point): Boolean =
    point.x >= 0 && point.x < size.x && point.y >= 0 && point.y < size.y
