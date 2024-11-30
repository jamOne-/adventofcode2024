import java.io.File

private fun getFile(fileName: String): File = File("src/main/kotlin/input/${fileName}.txt")

fun readInputLines(fileName: String): List<String> = getFile(fileName).readLines()
