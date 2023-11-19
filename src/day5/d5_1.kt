fun main() {
  val input = ""
  println(input.lines().filter { isNice(it) }.size)
}

val badSubstrings = listOf("ab", "cd", "pq", "xy")
val vowels = "aeiou"

fun isNice(s: String): Boolean {
  return !(badSubstrings.any { s.contains(it) } ||
           s.filter { vowels.contains(it) }.length < 3 ||
           s.windowed(2).none { it[0] == it[1] })
}
