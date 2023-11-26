fun main() {
  val input = ""
  val k = input.toInt() / 10
  // sieve[i] == 0 iff i is prime
  //          == k iff k is a prime divisor of i
  val sieve = sieve(k)
  val memo = Array<Map<Int, Int>?>(sieve.size) { null }
  for (i in 2 until sieve.size) {
    val s = sigma1(i, sieve, memo)
    if (s >= k) {
      println(i)
      break
    }
  }
}

fun sigma1(n: Int, sieve: Array<Int>, memo: Array<Map<Int, Int>?>): Int =
  primeDivisors(n, sieve, memo).map { (k, v) -> sumPowers(k, v) }.reduce(Int::times)
  
fun sumPowers(n: Int, maxPow: Int): Int = (0 .. maxPow).map { Math.pow(n.toDouble(), it.toDouble()).toInt() }.sum()

fun primeDivisors(n: Int, sieve: Array<Int>, memo: Array<Map<Int, Int>?>): Map<Int, Int> {
  val r = memo[n]
  if (r != null)
    return r
  val c = if (sieve[n] == 0)
            mapOf(n to 1)
          else
            primeDivisors(n / sieve[n], sieve, memo).toMutableMap().also { it.merge(sieve[n], 1, Int::plus) }
  memo[n] = c
  return c
}

fun sieve(n: Int): Array<Int> {
  val arr = Array(n + 1) { 0 }
  for (i in 2 .. n) {
    if (arr[i] == 0) {
      for (m in 2 * i .. n step i) {
        arr[m] = i
      }
    }
  }
  return arr
}
