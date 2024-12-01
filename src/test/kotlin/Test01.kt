import org.junit.Assert.assertEquals
import org.junit.Test

class Test01 {
    @Test
    fun test_a() {
        val result = solve01a(readInputLines("01_example1"))

        assertEquals(11, result)
    }

    @Test
    fun test_b() {
        val result = solve01b(readInputLines("01_example1"))

        assertEquals(31, result)
    }
}