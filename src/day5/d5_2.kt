fun main() {
  val input = ""
  println(input.lines().filter { isNice(it) }.size)
}


fun isNice(s: String): Boolean {
  val m = mutableMapOf<String, MutableList<Int>>()
  s.windowed(2).forEachIndexed { i, it -> m.computeIfAbsent(it) { mutableListOf<Int>() }.add(i) } 
  return m.values.any { list -> list.sorted().let { it.last() - it.first() } > 1 } &&
         s.windowed(3).any { it[0] == it[2] }
}
