import org.junit.Assert.assertEquals
import org.junit.Test

class Test03 {
    @Test
    fun test_a() {
        val result = solve03a(readInputLines("03_example1"))

        assertEquals(161, result)
    }

    @Test
    fun test_b() {
        val result = solve03b(readInputLines("03_example2"))

        assertEquals(48, result)
    }

    @Test
    fun test_b_againstReplaces() {
        val result = solve03b(listOf("mul(2,don't()do()2)"))

        assertEquals(0, result)
    }

    @Test
    fun test_b_real() {
        val result = solve03b(readInputLines("03"))

        assertEquals(107862689, result)
    }
}