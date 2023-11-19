fun main() {
  val input = ""
  var p = listOf(Pair(0, 0), Pair(0, 0))
  val visited = mutableSetOf(p[0])
  input.chunked(2).forEach { chunk ->
    p = chunk.mapIndexed { i, c -> 
      when(c) {
        '^' -> Pair(p[i].first, p[i].second + 1)
        '>' -> Pair(p[i].first + 1, p[i].second)
        'v' -> Pair(p[i].first, p[i].second - 1)
        '<' -> Pair(p[i].first - 1, p[i].second)
        else -> throw IllegalArgumentException("$c")
      }.also { visited.add(it) }
    }
  }
  println(visited.size)
}
