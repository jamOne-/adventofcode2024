import org.junit.Assert.assertEquals
import org.junit.Test

class Test15 {
    @Test
    fun test_a() {
        val result = solve15a(readInputLines("15_example1"))

        assertEquals(10092, result)
    }

    @Test
    fun test_a_smaller() {
        val result = solve15a(readInputLines("15_example2"))

        assertEquals(2028, result)
    }

    @Test
    fun test_b() {
        val result = solve15b(readInputLines("15_example1"))

        assertEquals(9021, result)
    }
}