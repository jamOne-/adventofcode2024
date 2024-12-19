import org.junit.Assert.assertEquals
import org.junit.Test

class Test19 {
    @Test
    fun test_a() {
        val result = solve19a(readInputLines("19_example1"))

        assertEquals(6, result)
    }

    @Test
    fun test_b() {
        val result = solve19b(readInputLines("19_example1"))

        assertEquals(16, result)
    }
}