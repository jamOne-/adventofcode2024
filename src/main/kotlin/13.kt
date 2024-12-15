fun main() {
    println(solve13a(readInputLines("13")))
    println(solve13b(readInputLines("13")))
}

fun solve13a(lines: List<String>): Int = parseInput(lines).mapNotNull { solveMachine(it) }.sum()
fun solve13b(lines: List<String>): Long = parseInput(lines, part2 = true).sumOf { solveMachine2(it) }

private val LINE_REGEX = Regex("""X[+=](\d+), Y[+=](\d+)""")
private fun parseLine(line: String): VectorL {
    val match = LINE_REGEX.find(line)
    checkNotNull(match)
    return VectorL(match.groupValues[1].toLong(), match.groupValues[2].toLong())
}

private data class Machine(val prize: PointL, val aButton: VectorL, val bButton: VectorL)

private val PART2_SHIFT = VectorL(10000000000000, 10000000000000)
private fun parseInput(lines: List<String>, part2: Boolean = false): List<Machine> =
    lines.filter { it.isNotEmpty() }.windowed(3, 3).map {
        Machine(
            aButton = parseLine(it[0]),
            bButton = parseLine(it[1]),
            prize = parseLine(it[2]).toPointL() + if (part2) PART2_SHIFT else VectorL(0, 0)
        )
    }

private val A_COST = 3
private val B_COST = 1
private val MAX_PRESSES = 100
private fun solveMachine(machine: Machine): Int? {
    val mem = mutableMapOf<Pair<PointL, Pair<Int, Int>>, Int?>()

    fun tmp(p: PointL, presses: Pair<Int, Int>): Int? {
        val argsPair = Pair(p, presses)
        if (argsPair in mem) {
            return mem[argsPair]
        }

        val result = if (p == machine.prize) {
            presses.first * A_COST + presses.second * B_COST
        } else if (p.x > machine.prize.x || p.y > machine.prize.y) {
            null
        } else {
            val candidates = mutableListOf<Int?>()
            if (presses.first < MAX_PRESSES) {
                candidates.add(tmp(p + machine.aButton, Pair(presses.first + 1, presses.second)))
            }
            if (presses.second < MAX_PRESSES) {
                candidates.add(tmp(p + machine.bButton, Pair(presses.first, presses.second + 1)))
            }

            candidates.filterNotNull().minOrNull()
        }

        mem[argsPair] = result
        return result
    }

    return tmp(PointL(0, 0), Pair(0, 0))
}

private fun solveMachine2(machine: Machine): Long {
    val aCount =
        (machine.bButton.y * machine.prize.x - machine.bButton.x * machine.prize.y) / (machine.bButton.y * machine.aButton.x - machine.bButton.x * machine.aButton.y)
    val bCount =
        (machine.aButton.x * machine.prize.y - machine.prize.x * machine.aButton.y) / (machine.bButton.y * machine.aButton.x - machine.bButton.x * machine.aButton.y)

    return if ((aCount * machine.aButton.x + bCount * machine.bButton.x == machine.prize.x) && (aCount * machine.aButton.y + bCount * machine.bButton.y == machine.prize.y)) {
        aCount * A_COST + bCount * B_COST
    } else 0
}
