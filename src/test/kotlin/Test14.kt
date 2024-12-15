import org.junit.Assert.assertEquals
import org.junit.Test

class Test14 {
    @Test
    fun test_a() {
        val result = solve14a(readInputLines("14_example1"), Vector(11, 7))

        assertEquals(12, result)
    }
}