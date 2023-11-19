fun main() {
  val input = ""
  println(input.lines()
    .map { l -> l.split("x").map { it.toInt() }.sorted() }
    .map { (it[0] + it[1]) * 2 + it[0] * it[1] * it[2] }
    .sum())
}
