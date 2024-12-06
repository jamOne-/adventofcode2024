import java.io.File

private fun getFile(fileName: String): File = File("src/main/kotlin/input/${fileName}.txt")

fun readInputLines(fileName: String): List<String> = getFile(fileName).readLines()

data class Point(val x: Int, val y: Int)
data class Vector(val x: Int, val y: Int)
