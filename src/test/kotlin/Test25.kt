import org.junit.Assert.assertEquals
import org.junit.Test

class Test25 {
    @Test
    fun test_a() {
        val result = solve25a(readInputLines("25_example1"))

        assertEquals(3, result)
    }
}