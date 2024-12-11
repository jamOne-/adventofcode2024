import org.junit.Assert.assertEquals
import org.junit.Test

class Test11 {
    @Test
    fun test_a() {
        val result = solve11a(readInputLines("11_example1"))

        assertEquals(55312, result)
    }

    @Test
    fun test_b() {
        val result = solve11b(readInputLines("11_example1"))

        assertEquals(65601038650482, result)
    }
}