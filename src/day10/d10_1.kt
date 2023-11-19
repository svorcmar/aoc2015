fun main() {
  val input = ""
  println(generateSequence(input) { lookAndSay(it) }.take(41).last().length)
}

fun lookAndSay(s: String): String {
  var prev = 'x'
  var cnt = 1
  val buffer = StringBuilder()
  for (ch in s) {
    if (ch == prev) {
      cnt++
    } else {
      buffer.append(cnt).append(prev)
      cnt = 1
      prev = ch
    }
  }
  return buffer.append(cnt).append(prev).toString().substring(2)
}
