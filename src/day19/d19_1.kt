fun main() {
  val input = ""
  val lines = input.lines()
  val rules = lines.dropLast(2).map { it.split(" ").let { it[0] to it[2] } }
  val start = lines.last()
  
  val result = mutableSetOf<String>()
  for (rule in rules) {
    var startIdx = 0
    while (startIdx >= 0) {
      startIdx = start.indexOf(rule.first, startIdx)
      if (startIdx >= 0) {
        result.add(start.replaceRange(startIdx, startIdx + rule.first.length, rule.second))
        startIdx++
      }
    }
  }
  println(result.size)
}
