fun main() {
  val input = ""
  val graph = input.lines().map { parseChange(it) }.groupBy { it.who }.mapValues { (_, v) -> v.associateBy({ it.nextTo }) { it.delta } }
  val keys = graph.keys.toList()
  println(forEachPermutation(keys.size) {
    (0 until keys.size).map { i ->
      keys[it[i]].let { who -> 
        (if (i == keys.size - 1) 0 else graph[who]!![keys[it[i + 1]]]!!) +
        (if (i == 0) 0 else graph[who]!![keys[it[i - 1]]]!!)
      }
    }.sum()
  }.max())
}

data class Change(val who: String, val nextTo: String, val delta: Int)

fun parseChange(s: String): Change {
    return s.split(" ").let { Change(it[0], it[10].dropLast(1), (if ("gain" == it[2]) 1 else -1) * it[3].toInt()) }
}

fun <T> forEachPermutation(n: Int, f: (Array<Int>) -> T): List<T> {
  val arr = Array<Int>(n) { 0 }
  return genRecursive(arr, 0, (0 until n).toSet(), f)
}

fun <T> genRecursive(arr: Array<Int>, nextIdx: Int, options: Set<Int>, f: (Array<Int>) -> T): List<T> {
  if (nextIdx == arr.size) {
    return listOf(f(arr))
  }
  val result = mutableListOf<T>()
  for (opt in options) {
    arr[nextIdx] = opt
    result.addAll(genRecursive(arr, nextIdx + 1, options - opt, f))
  }
  return result
}
