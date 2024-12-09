import org.junit.Assert.assertEquals
import org.junit.Test

class Test08 {
    @Test
    fun test_a() {
        val result = solve08a(readInputLines("08_example1"))

        assertEquals(14, result)
    }

    @Test
    fun test_b() {
        val result = solve08b(readInputLines("08_example1"))

        assertEquals(34, result)
    }
}