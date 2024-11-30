fun main() {
  println(solve0(readInputLines("00")))
}

fun solve0(lines: List<String>): Int = lines.map { it.toInt() }.fold(0) { a, b -> a + b }
