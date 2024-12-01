import kotlin.math.abs

fun main() {
    println(solve01a(readInputLines("01")))
    println(solve01b(readInputLines("01")))
}

fun solve01a(lines: List<String>): Int {
    val (xs, ys) = lines.map(::lineToPair).flattenPairs()

    return xs.sorted().zip(ys.sorted()).map(::distance).sum()
}

fun solve01b(lines: List<String>): Int {
    val (xs, ys) = lines.map(::lineToPair).flattenPairs()
    val rightCount = ys.groupingBy { it }.eachCount()

    return xs.fold(0) { similarity, x -> similarity + x * rightCount.getOrDefault(x, 0) }
}

private fun distance(pair: Pair<Int, Int>): Int = abs(pair.first - pair.second)

private fun <T, R> List<Pair<T, R>>.flattenPairs(): Pair<List<T>, List<R>> =
    this.fold(Pair(listOf(), listOf())) { result, pair -> Pair(result.first + pair.first, result.second + pair.second) }

private fun lineToPair(line: String): Pair<Int, Int> {
    val split = line.split("   ")
    assert(split.size == 2)

    return Pair(split[0].toInt(), split[1].toInt())
}