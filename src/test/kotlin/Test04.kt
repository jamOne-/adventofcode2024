import org.junit.Assert.assertEquals
import org.junit.Test

class Test04 {
    @Test
    fun test_a() {
        val result = solve04a(readInputLines("04_example1"))

        assertEquals(18, result)
    }

    @Test
    fun test_b() {
        val result = solve04b(readInputLines("04_example1"))

        assertEquals(9, result)
    }
}