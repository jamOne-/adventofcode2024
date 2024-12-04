fun main() {
    println(solve04a(readInputLines("04")))
    println(solve04b(readInputLines("04")))
}

fun solve04a(lines: List<String>): Int {
    var total = 0

    for ((y, line) in lines.withIndex()) {
        for (x in line.indices) {
            val start = Point(x, y)

            for (dy in -1..1) {
                for (dx in -1..1) {
                    if (dy == 0 && dx == 0) {
                        continue
                    }

                    if (checkXmas(lines, start, Point(dx, dy))) {
                        total += 1
                    }
                }
            }
        }
    }

    return total
}

fun solve04b(lines: List<String>): Int {
    var total = 0

    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            if (c != 'A') {
                continue
            }

            if ((checkMas(lines, Point(x - 1, y - 1), Point(1, 1)) || checkMas(
                    lines,
                    Point(x + 1, y + 1),
                    Point(-1, -1)
                ))
                && (checkMas(lines, Point(x + 1, y - 1), Point(-1, 1)) || checkMas(
                    lines,
                    Point(x - 1, y + 1),
                    Point(1, -1)
                ))
            ) {
                total += 1
            }
        }
    }

    return total
}

private fun checkWord(word: String, lines: List<String>, start: Point, delta: Point): Boolean {
    val lastX = start.x + delta.x * word.lastIndex
    val lastY = start.y + delta.y * word.lastIndex
    val withinBounds =
        start.x >= 0 && start.x < lines[0].length &&
                start.y >= 0 && start.y < lines.size &&
                lastX >= 0 && lastX < lines[0].length &&
                lastY >= 0 && lastY < lines.size

    return withinBounds && word.withIndex()
        .all { (i, c) -> lines[start.y + delta.y * i][start.x + delta.x * i] == c }
}

private fun checkXmas(lines: List<String>, start: Point, delta: Point): Boolean = checkWord("XMAS", lines, start, delta)

private fun checkMas(lines: List<String>, start: Point, delta: Point): Boolean = checkWord("MAS", lines, start, delta)