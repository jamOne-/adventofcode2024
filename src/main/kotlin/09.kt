import java.math.BigInteger

fun main() {
    println(solve09a(readInputLines("09")))
    println(solve09b(readInputLines("09")))
}

fun solve09a(lines: List<String>): BigInteger = checksum(fragmentBlocks(readInput(lines[0])))

fun solve09b(lines: List<String>): BigInteger = checksum(fragmentBlocks2(readInput(lines[0])))

private data class Block(val id: Int, val size: Int, val freeAfter: Int, val moved: Boolean = false)

private fun readInput(disk: String): List<Block> {
    val fixedDisk = if (disk.length % 2 != 0) disk + '0' else disk
    val result = mutableListOf<Block>()
    var id = 0

    for (s in fixedDisk.windowed(2, 2)) {
        val size = s[0].digitToInt()
        val free = s[1].digitToInt()
        result.add(Block(id, size, free))
        id += 1
    }

    return result
}

private fun fragmentBlocks(blocks: List<Block>): List<Block> {
    val bs = blocks.toMutableList()
    val result = mutableListOf(Block(blocks[0].id, blocks[0].size, 0))

    var i = 0
    var j = bs.lastIndex

    while (i < j) {
        if (bs[i].freeAfter > bs[j].size) {
            result.add(bs[j].copy(freeAfter = 0))
            bs[i] = bs[i].copy(freeAfter = bs[i].freeAfter - bs[j].size)
            j -= 1
        } else if (bs[i].freeAfter < bs[j].size) {
            result.add(Block(bs[j].id, bs[i].freeAfter, 0))
            bs[j] = bs[j].copy(size = bs[j].size - bs[i].freeAfter)
            i += 1
            result.add(bs[i].copy(freeAfter = 0))
        } else {
            result.add(bs[j].copy(freeAfter = 0))
            i += 1
            j -= 1
            if (i != j + 1) {
                result.add(bs[i].copy(freeAfter = 0))
            }
        }
    }


    return result
}

private fun fragmentBlocks2(blocks: List<Block>): List<Block> {
    val result = blocks.toMutableList()

    var i = blocks.lastIndex
    while (i > 0) {
        val ib = result[i]
        if (ib.moved) {
            i -= 1
            continue
        }

        var moved = false
        for (j in 0 until i) {
            val jb = result[j]
            if (jb.freeAfter >= ib.size) {
                result[i - 1] = result[i - 1].copy(freeAfter = result[i - 1].freeAfter + ib.freeAfter + ib.size)
                result.removeAt(i)
                result[j] = jb.copy(freeAfter = 0)
                result.add(j + 1, ib.copy(freeAfter = jb.freeAfter - ib.size, moved = true))
                moved = true
                break
            }
        }
        if (!moved) {
            i -= 1
        }
    }


    return result
}

private fun checksum(blocks: List<Block>): BigInteger {
    var total = BigInteger.ZERO
    var i = 0

    for (block in blocks) {
        for (j in 0 until block.size) {
            total += block.id.toBigInteger() * i.toBigInteger()
            i += 1
        }
        i += block.freeAfter
    }

    return total
}