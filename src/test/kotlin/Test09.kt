import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigInteger

class Test09 {
    @Test
    fun test_a() {
        val result = solve09a(readInputLines("09_example1"))

        assertEquals(BigInteger.valueOf(1928), result)
    }

    @Test
    fun test_b() {
        val result = solve09b(readInputLines("09_example1"))

        assertEquals(BigInteger.valueOf(2858), result)
    }
}