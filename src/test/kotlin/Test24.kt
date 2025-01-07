import org.junit.Assert.assertEquals
import org.junit.Test

class Test24 {
    @Test
    fun test_a() {
        val result = solve24a(readInputLines("24_example1"))

        assertEquals(4, result)
    }

    @Test
    fun test_a2() {
        val result = solve24a(readInputLines("24_example2"))

        assertEquals(2024, result)
    }
}