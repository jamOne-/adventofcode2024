import org.junit.Assert.assertEquals
import org.junit.Test

class Test10 {
    @Test
    fun test_a() {
        val result = solve10a(readInputLines("10_example1"))

        assertEquals(36, result)
    }

    @Test
    fun test_b() {
        val result = solve10b(readInputLines("10_example1"))

        assertEquals(81, result)
    }
}