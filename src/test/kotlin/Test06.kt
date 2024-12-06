import org.junit.Assert.assertEquals
import org.junit.Test

class Test06 {
    @Test
    fun test_a() {
        val result = solve06a(readInputLines("06_example1"))

        assertEquals(41, result)
    }

    @Test
    fun test_b() {
        val result = solve06b(readInputLines("06_example1"))

        assertEquals(6, result)
    }

    @Test
    fun test_b_real() {
        val result = solve06b(readInputLines("06"))

        assertEquals(1928, result)
    }
}