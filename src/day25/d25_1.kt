val first = 20151125L
val mul = 252533L
val mod = 33554393L

fun main() {
  val input = ""
  val (row, col) = input.split(" ").let { it[it.size - 3].dropLast(1).toLong() to it[it.size - 1].dropLast(1).toLong() }
  // index (1-based) of start of n-th row: 1 + n * (n - 1) / 2
  val startRow = col + row - 1
  val startRowIdx = 1 + startRow * (startRow - 1) / 2
  val targetIdx = startRowIdx + col - 1
  println((first * modularPow(mul, targetIdx - 1, mod)).mod(mod))
}

fun modularPow(n: Long, pow: Long, mod: Long): Long {
  var result = 1L
  var pow2 = 1L
  var nToPow2 = n
  while (true) {
    if (pow2 > pow)
      break
    if (pow2 and pow == pow2) {
      result = (result * nToPow2).mod(mod)
    }
    nToPow2 = (nToPow2 * nToPow2).mod(mod)
    pow2 *= 2
  }
  return result
}
