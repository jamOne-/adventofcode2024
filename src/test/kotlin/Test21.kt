import org.junit.Assert.assertEquals
import org.junit.Test

class Test21 {
    @Test
    fun test_a() {
        val result = solve21a(readInputLines("21_example1"))

        assertEquals(126384, result)
    }
}