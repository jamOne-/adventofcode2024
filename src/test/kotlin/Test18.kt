import org.junit.Assert.assertEquals
import org.junit.Test

class Test18 {
    @Test
    fun test_a() {
        val result = solve18a(readInputLines("18_example1"), Vector(7, 7), bytesCount = 12)

        assertEquals(22, result)
    }

    @Test
    fun test_b() {
        val result = solve18b(readInputLines("18_example1"), Vector(7, 7))

        assertEquals(Point(6, 1), result)
    }
}