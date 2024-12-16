import org.junit.Assert.assertEquals
import org.junit.Test

class Test16 {
    @Test
    fun test_a() {
        val result = solve16a(readInputLines("16_example1"))

        assertEquals(7036, result)
    }

    @Test
    fun test_a_smaller() {
        val result = solve16a(readInputLines("16_example2"))

        assertEquals(11048, result)
    }

    @Test
    fun test_b() {
        val result = solve16b(readInputLines("16_example1"))

        assertEquals(45, result)
    }

    @Test
    fun test_b_smaller() {
        val result = solve16b(readInputLines("16_example2"))

        assertEquals(64, result)
    }

    @Test
    fun test_b_my() {
        val result = solve16b(readInputLines("16_my"))

        assertEquals(264, result)
    }
}