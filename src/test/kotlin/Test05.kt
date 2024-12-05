import org.junit.Assert.assertEquals
import org.junit.Test

class Test05 {
    @Test
    fun test_a() {
        val result = solve05a(readInputLines("05_example1"))

        assertEquals(143, result)
    }

    @Test
    fun test_b() {
        val result = solve05b(readInputLines("05_example1"))

        assertEquals(123, result)
    }

    @Test
    fun test_b_real() {
        val result = solve05b(readInputLines("05"))

        assertEquals(4507, result)
    }
}