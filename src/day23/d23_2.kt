fun main() {
  val input = ""
  val instrs = input.lines().map { it.split(" ").let { Triple(it[0], 
                                                              if (it.size == 2) it[1] else it[1].dropLast(1),
                                                              if (it.size == 3) it[2] else "") } }
  var pc = 0
  var a = 1L
  var b = 0L
  while (pc < instrs.size) {
    var pcD = 1
    when(instrs[pc].first) {
      "hlf" -> {
        if (instrs[pc].second == "a")
          a /= 2
        else
          b /= 2
      }
      "tpl" -> {
        if (instrs[pc].second == "a")
          a *= 3
        else
          b *= 3
      }
      "inc" -> {
        if (instrs[pc].second == "a")
          a++
        else
          b++
      }
      "jmp" -> {
        pcD = instrs[pc].second.toInt()
      }
      "jie" -> {
        if (instrs[pc].second == "a" && a.mod(2) == 0)
          pcD = instrs[pc].third.toInt()
        if (instrs[pc].second == "b" && b.mod(2) == 0)
          pcD = instrs[pc].third.toInt()
      }
      "jio" -> {
        if (instrs[pc].second == "a" && a == 1L)
          pcD = instrs[pc].third.toInt()
        if (instrs[pc].second == "b" && b == 1L)
          pcD = instrs[pc].third.toInt()
      }
      else -> throw IllegalArgumentException(instrs[pc].first)
    }
    pc += pcD
  }
  println(b)
}
