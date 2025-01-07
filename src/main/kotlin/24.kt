fun main() {
    println(solve24a(readInputLines("24")))
    println(solve24b(readInputLines("24")))
}

fun solve24a(lines: List<String>): Long {
    val (map, operations) = parseInput(lines)

    for (op in sortOperations(operations)) {
        map[op.result] = op.execute(map[op.l]!!, map[op.r]!!)
    }

    return combineValue(map, "z")
}

fun solve24b(lines: List<String>): Boolean {
    val (map, operations) = parseInput(lines)

    for (op in sortOperations(operations)) {
        map[op.result] = op.execute(map[op.l]!!, map[op.r]!!)
    }

    val x = combineString(map, "x")
    val y = combineString(map, "y")
    val z = combineString(map, "z")
    val expected = (x.toLong(2) + y.toLong(2)).toString(2)

    println("x: 0$x")
    println("y: 0$y")
    println("z: $z")
    println("e: $expected")

    /**
     * Solved manually, answer:
     * mks XOR bhr -> vcv
     * x13 AND y13 -> z13
     *
     * vbw OR qkk -> z25
     * mqj XOR pqn -> mps
     *
     * csn AND nmn -> z19
     * csn XOR nmn -> vwp
     *
     * x33 AND y33 -> cqm
     * x33 XOR y33 -> vjv
     *
     * cqm,mps,vcv,vjv,vwp,z13,z19,z25
     */

    for (op in operations) {
        println("${op.l} -> ${op.result} [label=\"${op.toStringType()} ${op.r}\"];")
        println("${op.r} -> ${op.result} [label=\"${op.toStringType()} ${op.l}\"];")
    }

    return expected == z
}

private fun combineString(map: Map<String, Int>, prefix: String): String =
    map.entries.filter { it.key.startsWith(prefix) }.sortedByDescending { it.key }
        .joinToString(separator = "") { it.value.toString() }

private fun combineValue(map: Map<String, Int>, prefix: String): Long =
    map.entries.filter { it.key.startsWith(prefix) }.sortedByDescending { it.key }.map { it.value }
        .fold(0L) { result, b -> result * 2 + b }

private val OPERATION_LINE_REGEX = Regex("""^(\w+) (OR|XOR|AND) (\w+) -> (\w+)$""")

private data class Input24(val map: MutableMap<String, Int>, val operations: List<Operation>)

private fun parseInput(lines: List<String>): Input24 {
    val map = mutableMapOf<String, Int>()

    var i = 0
    while (lines[i].isNotEmpty()) {
        val split = lines[i].split(": ")
        map[split[0]] = split[1].toInt()
        i += 1
    }

    val operations = lines.drop(i + 1).map {
        val match = OPERATION_LINE_REGEX.find(it)
        checkNotNull(match)
        when (match.groupValues[2]) {
            "OR" -> OrOperation(match.groupValues[1], match.groupValues[3], match.groupValues[4])
            "XOR" -> XorOperation(match.groupValues[1], match.groupValues[3], match.groupValues[4])
            "AND" -> AndOperation(match.groupValues[1], match.groupValues[3], match.groupValues[4])
            else -> throw IllegalStateException()
        }
    }
    return Input24(map, operations)
}

private sealed interface Operation {
    val l: String
    val r: String
    val result: String
    fun execute(a: Int, b: Int): Int
}

private data class OrOperation(override val l: String, override val r: String, override val result: String) :
    Operation {
    override fun execute(a: Int, b: Int): Int = a.or(b)
}

private data class AndOperation(override val l: String, override val r: String, override val result: String) :
    Operation {
    override fun execute(a: Int, b: Int): Int = a.and(b)
}

private data class XorOperation(override val l: String, override val r: String, override val result: String) :
    Operation {
    override fun execute(a: Int, b: Int): Int = a.xor(b)
}

private fun sortOperations(operations: List<Operation>): List<Operation> {
    val allNodes = operations.flatMap { listOf(it.l, it.r, it.result) }.toSet()
    val ns = mutableMapOf<String, Set<String>>()
    for (op in operations) {
        ns[op.l] = ns.getOrDefault(op.l, setOf()) + op.result
        ns[op.r] = ns.getOrDefault(op.r, setOf()) + op.result
    }

    val visited = mutableSetOf<String>()
    val stack = ArrayDeque<String>()

    fun dfs(node: String) {
        visited.add(node)
        for (n in ns.getOrDefault(node, setOf())) {
            if (n !in visited) {
                dfs(n)
            }
        }
        stack.addLast(node)
    }
    for (node in allNodes) {
        if (node !in visited) {
            dfs(node)
        }
    }

    val nodeOrder: Map<String, Int> = stack.withIndex().associate { Pair(it.value, it.index) }

    return operations.sortedByDescending { nodeOrder[it.result]!! }
}

private fun Operation.toStringType() = when (this) {
    is OrOperation -> "OR"
    is XorOperation -> "XOR"
    is AndOperation -> "AND"
}
