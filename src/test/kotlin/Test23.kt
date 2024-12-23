import org.junit.Assert.assertEquals
import org.junit.Test

class Test23 {
    @Test
    fun test_a() {
        val result = solve23a(readInputLines("23_example1"))

        assertEquals(7, result)
    }

    @Test
    fun test_b() {
        val result = solve23b(readInputLines("23_example1"))

        assertEquals("co,de,ka,ta", result)
    }
}