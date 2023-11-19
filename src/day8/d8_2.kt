fun main() {
  val input = ""
  println(input.lines().map { escape(it).length - it.length }.sum())
}

fun escape(string: String): String {
  return "\"" + string.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
}
