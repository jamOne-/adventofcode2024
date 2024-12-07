import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigInteger

class Test07 {
    @Test
    fun test_a() {
        val result = solve07a(readInputLines("07_example1"))

        assertEquals(BigInteger.valueOf(3749), result)
    }

    @Test
    fun test_b() {
        val result = solve07b(readInputLines("07_example1"))

        assertEquals(BigInteger.valueOf(11387), result)
    }
}