fun main() {
  val input = ""
  val edges = input.lines().map { parseEdge(it) }.flatMap { e -> listOf(e, Edge(e.to, e.from, e.distance)) }
  val nodes = edges.map { e -> e.from }.toSet()
  val nodeToIdx = nodes.toList().sorted().mapIndexed { i, n -> n to i }.toMap()
  val edgesMap = edges.groupBy { e -> nodeToIdx[e.from]!! }
        .mapValues() { (_, v) -> v.groupBy { e -> nodeToIdx[e.to]!!}.mapValues { (_, v2) -> v2[0].distance } }
  
  println(forEachPermutation(nodes.size) { arr -> computeLength(arr, edgesMap) }.min())
}

data class Edge(val from: String, val to: String, val distance: Int)

fun parseEdge(string: String): Edge =
  string.split(" ").let { Edge(it[0], it[2], it[4].toInt()) }

fun computeLength(arr: Array<Int>, map: Map<Int, Map<Int, Int>>): Int {
  return arr.toList().windowed(2).map { it[0] to it[1] }.fold(0) { acc, (from, to) -> acc + map[from]!![to]!! }
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
