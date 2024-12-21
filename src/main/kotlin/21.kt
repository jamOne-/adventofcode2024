fun main() {
    println(solve21a(readInputLines("21")))
    println(solve21b(readInputLines("21")))
}

fun solve21a(lines: List<String>): Long = solve21ForDirPads(lines, dirPadsCount = 3)
fun solve21b(lines: List<String>): Long = solve21ForDirPads(lines, dirPadsCount = 26)

private fun solve21ForDirPads(lines: List<String>, dirPadsCount: Int): Long {
    var result = 0L

    for (code in lines) {
        var length = 0L
        var position = Mapping.NUMPAD.map['A']!!
        for (c in code) {
            length += shortestEncoding(Mapping.NUMPAD, n = dirPadsCount, c, position)
            position = Mapping.NUMPAD.map[c]!!
        }
        val codeInt = code.removeSuffix("A").toInt()

        result += length * codeInt
    }

    return result
}

private data class ShortestEncodingArgs(val mapping: Mapping, val n: Int, val c: Char, val from: Point)

private val SHORTEST_ENCODINGS_CACHE = mutableMapOf<ShortestEncodingArgs, Long>()
private fun shortestEncoding(mapping: Mapping, n: Int, c: Char, from: Point): Long {
    if (n == 0) {
        return 1
    }
    val args = ShortestEncodingArgs(mapping, n, c, from)
    if (args in SHORTEST_ENCODINGS_CACHE) {
        return SHORTEST_ENCODINGS_CACHE[args]!!
    }

    val dirs = getPossibleDirsCombinations(mapping, from, mapping.map[c]!!).map { it + 'A' }
    val result = dirs.minOf { ds ->
        ds.mapIndexed { i, c ->
            val f = if (i == 0) Mapping.DIRPAD.map['A']!! else Mapping.DIRPAD.map[ds[i - 1]]!!
            shortestEncoding(Mapping.DIRPAD, n - 1, c, f)
        }.sum()
    }

    SHORTEST_ENCODINGS_CACHE[args] = result
    return result
}

private data class GetDirsArgs(val mapping: Mapping, val from: Point, val dest: Point)

private val GET_DIRS_CACHE = mutableMapOf<GetDirsArgs, Set<String>>()
private fun getPossibleDirsCombinations(mapping: Mapping, from: Point, dest: Point): Set<String> {
    if (from == dest) {
        return setOf("")
    }
    val args = GetDirsArgs(mapping, from, dest)
    if (args in GET_DIRS_CACHE) {
        return GET_DIRS_CACHE[args]!!
    }

    val xDiff = dest.x - from.x
    val xDir = if (xDiff > 0) Direction.RIGHT else Direction.LEFT
    val yDiff = dest.y - from.y
    val yDir = if (yDiff > 0) Direction.DOWN else Direction.UP
    val result = mutableSetOf<String>()

    if (xDiff != 0) {
        val next = move(xDir, from)
        if (mapping.map[' '] != next) {
            result.addAll(getPossibleDirsCombinations(mapping, next, dest).map { xDir.toChar() + it })
        }
    }
    if (yDiff != 0) {
        val next = move(yDir, from)
        if (mapping.map[' '] != next) {
            result.addAll(getPossibleDirsCombinations(mapping, next, dest).map { yDir.toChar() + it })
        }
    }

    GET_DIRS_CACHE[args] = result
    return result
}

private fun Direction.toChar(): Char = when (this) {
    Direction.UP -> '^'
    Direction.RIGHT -> '>'
    Direction.DOWN -> 'v'
    Direction.LEFT -> '<'
}

private enum class Mapping(val map: Map<Char, Point>) {
    NUMPAD(
        mapOf(
            Pair('7', Point(0, 0)),
            Pair('8', Point(1, 0)),
            Pair('9', Point(2, 0)),
            Pair('4', Point(0, 1)),
            Pair('5', Point(1, 1)),
            Pair('6', Point(2, 1)),
            Pair('1', Point(0, 2)),
            Pair('2', Point(1, 2)),
            Pair('3', Point(2, 2)),
            Pair(' ', Point(0, 3)),
            Pair('0', Point(1, 3)),
            Pair('A', Point(2, 3)),
        )
    ),
    DIRPAD(
        mapOf(
            Pair(' ', Point(0, 0)),
            Pair('^', Point(1, 0)),
            Pair('A', Point(2, 0)),
            Pair('<', Point(0, 1)),
            Pair('v', Point(1, 1)),
            Pair('>', Point(2, 1)),
        )
    ),
}