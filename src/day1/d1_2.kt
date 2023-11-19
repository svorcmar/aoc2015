fun main() {
  val input = ""
  println(input.runningFoldIndexed(Pair(0, 0)) {i, s, c -> 
    if (s.second > 0)
      s;
    else if (c == '(')
      Pair(s.first + 1, 0)
    else if (s.first > 0)
      Pair(s.first - 1, 0)
    else
      Pair(-1, i + 1);
  }.last().second)
}
