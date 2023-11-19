fun main() {
  val input = ""
  println(input.lines().map { l -> 
    val dimensions = l.split("x").map { it.toInt() }
    listOf(
      dimensions[0] * dimensions[1],
      dimensions[1] * dimensions[2],
      dimensions[2] * dimensions[0]
    )
  }.map { it.map { side -> side * 2 }.sum() + it.min() }
   .sum())
}
