import java.io.File

private fun getFile(fileName: String): File = File("src/main/kotlin/input/${fileName}.txt")

fun readInputLines(fileName: String): List<String> = getFile(fileName).readLines()

data class Point(val x: Int, val y: Int)
data class Vector(val x: Int, val y: Int)

fun move(direction: Direction, position: Point): Point =
    Point(position.x + direction.delta.x, position.y + direction.delta.y)

enum class Direction(val delta: Vector) {
    UP(Vector(0, -1)),
    DOWN(Vector(0, 1)),
    RIGHT(Vector(1, 0)),
    LEFT(Vector(-1, 0))
}
