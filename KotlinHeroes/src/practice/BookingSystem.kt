package practice

// https://kotlinlang.org/docs/tutorials/competitive-programming.html
// https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin

import java.io.*
import java.lang.Math.max
import java.util.*

private fun readln() = readLine()!!
private fun readlnByte() = readln().toByte()
private fun readlnShort() = readln().toShort()
private fun readlnInt() = readln().toInt()
private fun readlnLong() = readln().toLong()
private fun readlnFloat() = readln().toFloat()
private fun readlnDouble() = readln().toDouble()
private fun readlnBigInt(radix: Int = 10) = readln().toBigInteger(radix)
private fun readlnBigDecimal() = readln().toBigDecimal()

private fun lineSequence(limit: Int = Int.MAX_VALUE) = generateSequence { readLine() }.constrainOnce().take(limit)
private fun readlnStrings() = readln().split(' ')
private fun readlnBytes() = readlnStrings().map { it.toByte() }
private fun readlnShorts() = readlnStrings().map { it.toShort() }
private fun readlnInts() = readlnStrings().map { it.toInt() }
private fun readlnLongs() = readlnStrings().map { it.toLong() }
private fun readlnFloats() = readlnStrings().map { it.toFloat() }
private fun readlnDoubles() = readlnStrings().map { it.toDouble() }

private fun readByteArray() = readlnStrings().run { ByteArray(size) { get(it).toByte() } }
private fun readShortArray() = readlnStrings().run { ShortArray(size) { get(it).toShort() } }
private fun readIntArray() = readlnStrings().run { IntArray(size) { get(it).toInt() } }
private fun readLongArray() = readlnStrings().run { LongArray(size) { get(it).toLong() } }
private fun readFloatArray() = readlnStrings().run { FloatArray(size) { get(it).toFloat() } }
private fun readDoubleArray() = readlnStrings().run { DoubleArray(size) { get(it).toDouble() } }

private fun readlnByteArray(n: Int) = ByteArray(n) { readlnByte() }
private fun readlnShortArray(n: Int) = ShortArray(n) { readlnShort() }
private fun readlnIntArray(n: Int) = IntArray(n) { readlnInt() }
private fun readlnLongArray(n: Int) = LongArray(n) { readlnLong() }
private fun readlnFloatArray(n: Int) = FloatArray(n) { readlnFloat() }
private fun readlnDoubleArray(n: Int) = DoubleArray(n) { readlnDouble() }

private fun readByteArray2d(rows: Int, cols: Int) = Array(rows) { readByteArray().also { require(it.size == cols) } }
private fun readShortArray2d(rows: Int, cols: Int) = Array(rows) { readShortArray().also { require(it.size == cols) } }
private fun readLongArray2d(rows: Int, cols: Int) = Array(rows) { readLongArray().also { require(it.size == cols) } }
private fun readIntArray2d(rows: Int, cols: Int) = Array(rows) { readIntArray().also { require(it.size == cols) } }
private fun readFloatArray2d(rows: Int, cols: Int) = Array(rows) { readFloatArray().also { require(it.size == cols) } }
private fun readDoubleArray2d(rows: Int, cols: Int) =
    Array(rows) { readDoubleArray().also { require(it.size == cols) } }


private fun isWhiteSpace(c: Char) = c in " \r\n\t"

// JVM-only targeting code follows next

// readString() via sequence is still slightly faster than Scanner
private fun readString() = generateSequence { System.`in`.read().toChar() }
    .dropWhile { isWhiteSpace(it) }.takeWhile { !isWhiteSpace(it) }.joinToString("")

private fun readByte() = readString().toByte()
private fun readShort() = readString().toShort()
private fun readInt() = readString().toInt()
private fun readLong() = readString().toLong()
private fun readFloat() = readString().toFloat()
private fun readDouble() = readString().toDouble()
private fun readBigInt(radix: Int = 10) = readString().toBigInteger(radix)
private fun readBigDecimal() = readString().toBigDecimal()

private fun readBytes(n: Int) = generateSequence { readByte() }.take(n)
private fun readShorts(n: Int) = generateSequence { readShort() }.take(n)
private fun readInts(n: Int) = generateSequence { readInt() }.take(n)
private fun readLongs(n: Int) = generateSequence { readLong() }.take(n)
private fun readFloats(n: Int) = generateSequence { readFloat() }.take(n)
private fun readDoubles(n: Int) = generateSequence { readDouble() }.take(n)

private fun printIntArray(a: IntArray) {
    println(a.joinToString(", "))
}

private fun printLongArray(a: LongArray) {
    println(a.joinToString(", "))
}

private fun printDoubleArray(a: DoubleArray) {
    println(a.joinToString(", "))
}

private fun printFloatArray(a: FloatArray) {
    println(a.joinToString(", "))
}

private fun printStringArray(a: Array<String>) {
    println(a.joinToString(", "))
}

private fun main() {
    val n = readlnInt()
    val visitors = mutableListOf<Visitor>()
    for (i in 1..n) {
        val (c, r) = readlnInts()
        visitors.add(Visitor(c, r, i))
    }
    visitors.sortByDescending { it.p }
    val k = readlnInt()
    val r = readlnInts()
    val tm = TreeMap<Int, Queue<Int>>()
    for (i in 1..k) {
        val q = tm.getOrDefault(r[i - 1], LinkedList())
        q.offer(i)
        tm[r[i - 1]] = q
    }
    var totalReq = 0
    var totalPayment = 0
    val ans = mutableListOf<IntArray>()
    for (i in 1..n) {
        val e = tm.ceilingEntry(visitors[i - 1].c)
        if (e != null) {
            totalPayment += visitors[i - 1].p
            totalReq++
            ans.add(intArrayOf(visitors[i - 1].i, e.value.poll()))
            if (e.value.isEmpty()) {
                tm.remove(e.key)
            }
        }
    }
    ans.sortBy { it[0] }
    println("$totalReq $totalPayment")
    for (a in ans) {
        println("${a[0]} ${a[1]}")
    }
}

data class Visitor(val c: Int, val p: Int, val i: Int)
