import java.io.File

private fun getFile(fileName: String): File = File("src/main/kotlin/input/${fileName}.txt")

fun readInputLines(fileName: String): List<String> = getFile(fileName).readLines()

data class Point(val x: Int, val y: Int) {
    operator fun plus(v: Vector): Point = Point(x + v.x, y + v.y)
    operator fun rem(v: Vector): Point {
        val newX = x % v.x
        val newY = y % v.y
        return Point(
            if (newX < 0) newX + v.x else newX,
            if (newY < 0) newY + v.y else newY
        )
    }
}
data class PointL(val x: Long, val y: Long) {
    operator fun plus(v: VectorL): PointL = PointL(x + v.x, y + v.y)
}
data class Vector(val x: Int, val y: Int) {
    operator fun times(n: Int): Vector =
        Vector(x * n, y * n)

    operator fun rem(v: Vector): Vector {
        val newX = x % v.x
        val newY = y % v.y
        return Vector(
            if (newX < 0) newX + v.x else newX,
            if (newY < 0) newY + v.y else newY
        )
    }
}
data class VectorL(val x: Long, val y: Long)

fun Vector.toPoint(): Point = Point(this.x, this.y)
fun VectorL.toPointL(): PointL = PointL(this.x, this.y)

fun move(direction: Direction, position: Point): Point =
    Point(position.x + direction.delta.x, position.y + direction.delta.y)

enum class Direction(val delta: Vector) {
    UP(Vector(0, -1)),
    DOWN(Vector(0, 1)),
    RIGHT(Vector(1, 0)),
    LEFT(Vector(-1, 0))
}
