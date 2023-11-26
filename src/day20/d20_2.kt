fun main() {
  val input = ""
  val k = input.toInt()
  val ceil = Math.ceil(input.toDouble()).toInt() / 11
  // sieve[i] == 0 iff i is prime
  //          == k iff k is a prime divisor of i
  val sieve = sieve(ceil)
  val memo = Array<Map<Int, Int>?>(sieve.size) { null }
  for (i in 2 until sieve.size) {
    val s = elfFunction(i, sieve, memo)
    if (s >= k) {
      println(i)
      break
    }
  }
}

fun elfFunction(n: Int, sieve: Array<Int>, memo: Array<Map<Int, Int>?>): Int =
  11 * allDivisors(primeDivisors(n, sieve, memo)).filter { n / it <= 50 }.sum()

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

fun allDivisors(primeDivs: Map<Int, Int>) = allDivisorsRec(primeDivs.entries.map { (k, v) -> k to v }.toList(), 0, 1)

fun allDivisorsRec(primeDivs: List<Pair<Int, Int>>, idx: Int, acc: Int): Sequence<Int> = sequence {
  if (idx == primeDivs.size) {
    yield(acc)
  } else {
    for (i in 0 .. primeDivs[idx].second) {
      yieldAll(allDivisorsRec(primeDivs, idx + 1, acc * Math.pow(primeDivs[idx].first.toDouble(), i.toDouble()).toInt()))
    }
  }
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
