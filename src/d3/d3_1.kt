fun main() {
  val input = ""
  var p = Pair(0, 0)
  val visited = mutableSetOf(p)
  input.forEach { c ->
    p = when(c) {
      '^' -> Pair(p.first, p.second + 1)
      '>' -> Pair(p.first + 1, p.second)
      'v' -> Pair(p.first, p.second - 1)
      '<' -> Pair(p.first - 1, p.second)
      else -> throw IllegalArgumentException("$c")
    }
    visited.add(p)
  }
  println(visited.size)
}
