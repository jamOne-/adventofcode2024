fun main() {
    println(solve06a(readInputLines("06")))
    println(solve06b(readInputLines("06")))
}

fun solve06a(lines: List<String>): Int {
    val (maze, start) = parseInput(lines)
    return walk(maze, start).size
}

fun solve06b(lines: List<String>): Int {
    val (maze, start) = parseInput(lines)
    val locationsForObstacle = walk(maze, start).minus(start)
    return locationsForObstacle.filter { walkingInCircle(addObstacle(maze, it), start) }.size
}

private typealias Maze = Map<Point, Char>

private fun parseInput(lines: List<String>): Pair<Maze, Point> {
    val map = mutableMapOf<Point, Char>()
    var start: Point? = null

    for ((y, line) in lines.withIndex()) {
        for ((x, c) in line.withIndex()) {
            map[Point(x, y)] = c
            if (c == '^') {
                start = Point(x, y)
            }
        }
    }

    return Pair(map, checkNotNull(start))
}

private fun addObstacle(map: Maze, position: Point): Maze {
    val newMap = map.toMutableMap()
    newMap[position] = '#'
    return newMap
}

private fun walk(map: Maze, start: Point): Set<Point> {
    val visited = mutableSetOf<Point>()
    var position = start
    var direction = Direction.UP

    while (!walkEnded(map, position)) {
        visited.add(position)

        val forward = move(direction, position)
        if (map.getOrDefault(forward, '.') == '#') {
            direction = rotateRight(direction)
        } else {
            position = forward
        }
    }

    return visited
}

private fun walkingInCircle(map: Maze, start: Point): Boolean {
    val visited = mutableSetOf<Pair<Point, Direction>>()
    var position = start
    var direction = Direction.UP

    while (!visited.contains(Pair(position, direction))) {
        visited.add(Pair(position, direction))

        val forward = move(direction, position)
        if (walkEnded(map, forward)) {
            return false
        } else if (map[forward] == '#') {
            direction = rotateRight(direction)
        } else {
            position = forward
        }
    }

    return true
}

private fun walkEnded(map: Maze, position: Point): Boolean = !map.containsKey(position)

private fun move(direction: Direction, position: Point): Point =
    Point(position.x + direction.delta.x, position.y + direction.delta.y)

private enum class Direction(val delta: Vector) {
    UP(Vector(0, -1)),
    DOWN(Vector(0, 1)),
    RIGHT(Vector(1, 0)),
    LEFT(Vector(-1, 0))
}

private fun rotateRight(direction: Direction): Direction =
    when (direction) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }