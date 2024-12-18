fun main() {
    println(solve17a(readInputLines("17")))
    println(solve17b())
}

fun solve17a(lines: List<String>): String {
    var (state, instructions) = parseInput(lines)

    while (state.i < instructions.size) {
        val instruction = Instruction(instructions[state.i], instructions[state.i + 1])
        state = update(state.copy(i = state.i + 2), instruction)
    }

    return state.output.joinToString(separator = ",")
}

fun solve17b(): Long {
    /*
        2,4,1,3,7,5,1,5,0,3,4,1,5,5,3,0
        while a != 0
            b = a % 8
            b = b ^ 3
            c = a >> b
            b = b ^ 5
            a = a >> 3
            b = b ^ c
            print(b % 8)
     */

    val program = listOf(2, 4, 1, 3, 7, 5, 1, 5, 0, 3, 4, 1, 5, 5, 3, 0)

    fun runIteration(a: Long): Int {
        var b = a % 8L
        b = b.xor(3)
        val c = a.shr(b.toInt())
        b = b.xor(5)
        b = b.xor(c)
        return (b % 8).toInt()
    }

    fun rec(a: Long, i: Int): Long? {
        if (i == -1) {
            return a
        }

        for (n in 0..7) {
            val newA = a.shl(3) + n

            if (runIteration(newA) == program[i]) {
                val result = rec(newA, i - 1)
                if (result != null) {
                    return result
                }
            }
        }

        return null
    }

    for (a in 0L..1023L) {
        if (runIteration(a) == program.last()) {
            val result = rec(a, program.lastIndex - 1)
            if (result != null) {
                return result
            }
        }
    }

    throw IllegalStateException("No solution found")
}

private data class ComputerState(val a: Long, val b: Long, val c: Long, val i: Int, val output: List<Int>)
private data class Input17(val state: ComputerState, val instructions: List<Int>)
private data class Instruction(val opcode: Int, val operand: Int)

private fun update(state: ComputerState, instruction: Instruction): ComputerState = when (instruction.opcode) {
    // adv:
    0 -> state.copy(
        a = state.a.shr(getOperandValue(state, instruction.operand).toInt())
    )
    // bxl:
    1 -> state.copy(
        b = state.b.xor(instruction.operand.toLong())
    )
    // bst:
    2 -> state.copy(
        b = getOperandValue(state, instruction.operand) % 8
    )
    // jnz:
    3 -> if (state.a == 0L) state else state.copy(
        i = instruction.operand
    )
    // bxc:
    4 -> state.copy(
        b = state.b.xor(state.c)
    )
    // out:
    5 -> state.copy(
        output = state.output + (getOperandValue(state, instruction.operand) % 8).toInt()
    )
    // bdv:
    6 -> state.copy(
        b = state.a.shr(getOperandValue(state, instruction.operand).toInt())
    )
    // cdv:
    7 -> state.copy(
        c = state.a.shr(getOperandValue(state, instruction.operand).toInt())
    )

    else -> throw IllegalStateException("Unknown instruction $instruction")
}

private fun getOperandValue(state: ComputerState, operand: Int): Long = when (operand) {
    in 0..3 -> operand.toLong()
    4 -> state.a
    5 -> state.b
    6 -> state.c
    else -> throw IllegalStateException("Illegal operand code $operand")
}

private val REGISTER_LINE_REGEX = Regex("""^Register [ABC]: (-?\d+)$""")
private val INSTRUCTIONS_LINE_REGEX = Regex("""^Program: (.*)$""")
private fun parseInput(lines: List<String>): Input17 {
    assert(lines.size == 5)
    val a = checkNotNull(REGISTER_LINE_REGEX.find(lines[0])).groupValues[1].toLong()
    val b = checkNotNull(REGISTER_LINE_REGEX.find(lines[1])).groupValues[1].toLong()
    val c = checkNotNull(REGISTER_LINE_REGEX.find(lines[2])).groupValues[1].toLong()
    val instructions = checkNotNull(INSTRUCTIONS_LINE_REGEX.find(lines[4])).groupValues[1].split(",").map { it.toInt() }

    return Input17(
        ComputerState(a, b, c, 0, listOf()), instructions
    )
}
