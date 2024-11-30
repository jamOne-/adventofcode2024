import org.junit.Assert.assertEquals
import org.junit.Test

class Test00 {
    @Test
    fun test() {
        val result = solve0(readInputLines("00"))

        assertEquals(6, result)
    }
}