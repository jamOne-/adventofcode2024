fun main() {
    println(solve15a(readInputLines("15")))
    println(solve15b(readInputLines("15")))
}

fun solve15a(lines: List<String>): Int = solve(lines, part2 = false)
fun solve15b(lines: List<String>): Int = solve(lines, part2 = true)

private fun solve(lines: List<String>, part2: Boolean): Int {
    val input = parseInput(lines, part2)
    val moves = input.moves
    val map = input.map.toMutableMap()
    var fish = input.fish

    for (move in moves) {
        fish = move(fish, move, map)
    }

    return map.entries.filter { it.value is Box || it.value is BoxLeft }.sumOf { it.key.toGPS() }
}

private sealed interface Field
private object Wall : Field
private object Empty : Field
private object Fish : Field
private object Box : Field
private object BoxLeft : Field
private object BoxRight : Field

private data class Input15(val map: Map<Point, Field>, val fish: Point, val moves: List<Direction>)

private fun parseInput(lines: List<String>, part2: Boolean = false): Input15 {
    val map = mutableMapOf<Point, Field>()
    var fish: Point? = null

    var i = 0
    while (lines[i].isNotEmpty()) {
        for ((x, c) in lines[i].withIndex()) {
            val p = Point((if (part2) 2 else 1) * x, i)
            val field = when (c) {
                '#' -> Wall
                '.' -> Empty
                '@' -> Fish
                'O' -> Box
                else -> throw IllegalStateException("Illegal character $c at ($x, $i)")
            }

            if (field is Fish) {
                fish = p
            }

            if (part2) {
                val (l, r) = when (field) {
                    Fish -> Pair(Fish, Empty)
                    Box -> Pair(BoxLeft, BoxRight)
                    else -> Pair(field, field)
                }

                map[p] = l
                map[p + Vector(1, 0)] = r

            } else {
                map[p] = field
            }
        }

        i += 1
    }

    val moves = lines.subList(i + 1, lines.size).flatMap { it.asIterable() }.map {
        when (it) {
            '^' -> Direction.UP
            'v' -> Direction.DOWN
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            else -> throw IllegalStateException("Illegal character $it")
        }
    }

    return Input15(map, checkNotNull(fish), moves)
}

private fun move(p: Point, d: Direction, map: MutableMap<Point, Field>): Point {
    val field = checkNotNull(map[p])
    val next = move(d, p)
    return if (canMove(p, d, map) && field !is Empty) {
        if ((field is BoxLeft || field is BoxRight) && (d == Direction.DOWN || d == Direction.UP)) {
            val l = if (field is BoxRight) p + Vector(-1, 0) else p
            val r = if (field is BoxLeft) p + Vector(1, 0) else p
            val nextL = move(d, l)
            val nextR = move(d, r)

            move(nextL, d, map)
            move(nextR, d, map)
            map[nextL] = BoxLeft
            map[nextR] = BoxRight
            map[l] = Empty
            map[r] = Empty

            next
        } else {
            move(next, d, map)
            map[next] = checkNotNull(map[p])
            map[p] = Empty
            next
        }
    } else {
        p
    }
}

private fun canMove(p: Point, d: Direction, map: Map<Point, Field>): Boolean = when (checkNotNull(map[p])) {
    is Wall -> false
    is Empty -> true
    is BoxLeft -> ((d == Direction.DOWN || d == Direction.UP) && canMove(
        move(d, p + Vector(1, 0)), d, map
    ) || d == Direction.LEFT || d == Direction.RIGHT) && canMove(move(d, p), d, map)

    is BoxRight -> ((d == Direction.DOWN || d == Direction.UP) && canMove(
        move(d, p + Vector(-1, 0)), d, map
    ) || d == Direction.LEFT || d == Direction.RIGHT) && canMove(move(d, p), d, map)

    else -> canMove(move(d, p), d, map)
}

private fun Point.toGPS(): Int = y * 100 + x
