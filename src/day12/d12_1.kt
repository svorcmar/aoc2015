fun main() {
  val input = ""
  println("(-?[0-9]+)".toRegex().findAll(input).map { it.groupValues[1].toInt() }.sum())
}
