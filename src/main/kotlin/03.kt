fun main() {
    println(solve03a(readInputLines("03")))
    println(solve03b(readInputLines("03")))
}

fun solve03a(lines: List<String>): Int =
    lines.flatMap(::tokenizeLine).filterIsInstance<Multiply>().sumOf { it.x * it.y }

fun solve03b(lines: List<String>): Int =
    lines.flatMap(::tokenizeLine).fold(State(true, 0)) { state, token ->
        when (token) {
            is Do -> State(true, state.total)
            is Dont -> State(false, state.total)
            is Multiply -> if (state.enabled) {
                State(state.enabled, state.total + token.x * token.y)
            } else {
                state
            }
        }
    }.total

private data class State(val enabled: Boolean, val total: Int)

private sealed interface Token
private data class Multiply(val x: Int, val y: Int) : Token
private object Do : Token
private object Dont : Token

private val ALL_TOKENS_REGEX = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")
private fun tokenizeLine(line: String): List<Token> =
    ALL_TOKENS_REGEX.findAll(line).map {
        when (it.value) {
            "do()" -> Do
            "don't()" -> Dont
            else -> Multiply(it.groupValues[1].toInt(), it.groupValues[2].toInt())
        }
    }.toList()
