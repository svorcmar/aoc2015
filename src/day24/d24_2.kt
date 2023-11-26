fun main() {
  val input = ""
  val list = input.lines().map { it.toInt() }
  val groupSize = list.sum() / 4
  val firstGroup = solve(list, groupSize)
  // order by quantum entanglement and find smallest one for which
  // the rest of packages can be divided in three groups of the same weight
  val candidates = firstGroup.map { it.map(Int::toLong).reduce(Long::times) to it }.sortedBy { it.first }
  for (candidate in candidates) {
    if (canBeSplitIn3(list - candidate.second, groupSize)) {
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

fun canBeSplitIn3(packages: List<Int>, target: Int): Boolean {
  // the order/size of the groups is not important;
  // the first element is forced the first group and the second element to the first or second group to fix the ordering
  return canBeSplitIn3Rec(packages, target, 2, packages[0] + packages[1], 0, 0) || 
         canBeSplitIn3Rec(packages, target, 2, packages[0], packages[1], 0)
}

fun canBeSplitIn3Rec(packages: List<Int>, target: Int, nextIdx: Int, g1: Int, g2: Int, g3: Int): Boolean {
  if (nextIdx == packages.size) {
    return true
  }
  val next = packages[nextIdx]
  return (g1 < target && g1 + next <= target && canBeSplitIn3Rec(packages, target, nextIdx + 1, g1 + next, g2, g3)) ||
         (g2 < target && g2 + next <= target && canBeSplitIn3Rec(packages, target, nextIdx + 1, g1, g2 + next, g3)) ||
         (g3 < target && g3 + next <= target && canBeSplitIn3Rec(packages, target, nextIdx + 1, g1, g2, g3 + next))
}
