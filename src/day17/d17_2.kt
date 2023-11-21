fun main() {
  val input = ""
  val jugs = input.lines().map { it.toInt() }
  // dynamic programming array
  // dp[n][r][c] = number of ways to fill capacity "c" when using exactly "r" of the first "n" jugs
  val dp = Array(jugs.size + 1) { Array(jugs.size + 1) { Array(150 + 1) { 0 } } }
  (1 until dp.size).forEach { n ->
    (1 until dp[n].size).forEach { r ->
      (1 until dp[n][r].size).forEach { c -> 
        dp[n][r][c] = dp[n - 1][r][c] + 
          if (jugs[n - 1] > c)
            0
          else if (jugs[n - 1] < c)
            dp[n - 1][r - 1][c - jugs[n - 1]]
          else
            1
      }
    }
  }
  println(dp.last().map { it.last() }.find { it > 0 })
}
