import org.junit.Assert.assertEquals
import org.junit.Test

class Test13 {
    @Test
    fun test_a() {
        val result = solve13a(readInputLines("13_example1"))

        assertEquals(480, result)
    }
}