import org.junit.Assert.assertEquals
import org.junit.Test

class Test17 {
    @Test
    fun test_a() {
        val result = solve17a(readInputLines("17_example1"))

        assertEquals("4,6,3,5,6,3,5,2,1,0", result)
    }

    @Test
    fun test_b() {
        val resultA = solve17b()
        val output = solve17a(
            listOf(
                "Register A: $resultA",
                "Register B: 0",
                "Register C: 0",
                "",
                "Program: 2,4,1,3,7,5,1,5,0,3,4,1,5,5,3,0"
            )
        )

        assertEquals("2,4,1,3,7,5,1,5,0,3,4,1,5,5,3,0", output)
    }
}