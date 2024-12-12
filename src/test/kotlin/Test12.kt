import org.junit.Assert.assertEquals
import org.junit.Test

class Test12 {
    @Test
    fun test_a() {
        val result = solve12a(readInputLines("12_example1"))

        assertEquals(140, result)
    }

    @Test
    fun test_a2() {
        val result = solve12a(readInputLines("12_example2"))

        assertEquals(772, result)
    }

    @Test
    fun test_a3() {
        val result = solve12a(readInputLines("12_example3"))

        assertEquals(1930, result)
    }

    @Test
    fun test_b() {
        val result = solve12b(readInputLines("12_example1"))

        assertEquals(80, result)
    }

    @Test
    fun test_b2() {
        val result = solve12b(readInputLines("12_example2"))

        assertEquals(436, result)
    }

    @Test
    fun test_b3() {
        val result = solve12b(readInputLines("12_example3"))

        assertEquals(1206, result)
    }

    @Test
    fun test_b4() {
        val result = solve12b(readInputLines("12_example4"))

        assertEquals(236, result)
    }

    @Test
    fun test_b5() {
        val result = solve12b(readInputLines("12_example5"))

        assertEquals(368, result)
    }
}