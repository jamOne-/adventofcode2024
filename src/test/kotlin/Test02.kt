import org.junit.Assert.assertEquals
import org.junit.Test

class Test02 {
    @Test
    fun test_a() {
        val result = solve02a(readInputLines("02_example1"))

        assertEquals(2, result)
    }

    @Test
    fun test_b() {
        val result = solve02b(readInputLines("02_example1"))

        assertEquals(4, result)
    }

    @Test
    fun test_b_real() {
        val result = solve02b(readInputLines("02"))

        assertEquals(520, result)
    }
}