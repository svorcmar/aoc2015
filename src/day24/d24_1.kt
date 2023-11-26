fun main() {
  val input = ""
  val list = input.lines().map { it.toInt() }
  val groupSize = list.sum() / 3
  val firstGroup = solve(list, groupSize)
  // order by quantum entanglement and find smallest on for which
  // the rest of packages can be divided in two groups of the same weight
  val candidates = firstGroup.map { it.map(Int::toLong).reduce(Long::times) to it }.sortedBy { it.first }
  for (candidate in candidates) {
    if (solve(list - candidate.second, groupSize).isNotEmpty()) {
      println(candidate.first)
      break
    }
  }
}

fun solve(packages: List<Int>, target: Int): List<List<Int>> {
  // dp[i][j] == set of the smallest subsets of the first i elements that sum to j
  val dp = Array(packages.size + 1) { Array(target + 1) { 
    if (it == 0) 
      0             to listOf(emptyList<Int>())
    else 
      packages.size to emptyList<List<Int>>()
    }
  }
  packages.forEachIndexed { i0, n ->
    val i = i0 + 1
    for (j in 1 .. target) {
      dp[i][j] = dp[i - 1][j]
      if (j >= n) {
        val prevRow = dp[i - 1][j - n]
        if (prevRow.first + 1 < dp[i][j].first) {
          dp[i][j] = prevRow.first + 1 to prevRow.second.map { it -> it + n }
        } else if (prevRow.first + 1 == dp[i][j].first) {
          dp[i][j] = dp[i][j].first to dp[i][j].second + prevRow.second.map { it -> it + n }
        }
      }
    }
  }
  return dp[packages.size][target].second
}
