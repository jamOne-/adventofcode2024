import org.junit.Assert.assertEquals
import org.junit.Test

class Test22 {
    @Test
    fun test_a() {
        val result = solve22a(readInputLines("22_example1"))

        assertEquals(37327623, result)
    }

    @Test
    fun test_b() {
        val result = solve22b(readInputLines("22_example2"))

        assertEquals(23, result)
    }
}