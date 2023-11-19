fun main() {
  val input = ""
  val grid = Array(1000) { Array(1000) { false } }
  val instructions = parseInstructions(input)
  instructions.forEach {
    for (i in Math.min(it.p0.first, it.p1.first) .. Math.max(it.p0.first, it.p1.first)) {
      for (j in Math.min(it.p0.second, it.p1.second) .. Math.max(it.p0.second, it.p1.second)) {
        grid[i][j] = when(it.command) {
          Command.ON     -> true
          Command.OFF    -> false
          Command.TOGGLE -> !grid[i][j]
        }
      }
    }
  }
  println(grid.fold(0) { acc0, it -> acc0 + it.fold(0) { acc, b -> acc + if (b) 1 else 0 } })
}

enum class Command { ON, OFF, TOGGLE }
data class Instruction(val p0: Pair<Int, Int>, val p1: Pair<Int, Int>, val command: Command)

fun parseInstructions(input: String): List<Instruction> {
  return input.lines().map { line ->
    val tokens = line.split(" ")
    val command = when(tokens[1]) {
      "on"  -> Command.ON
      "off" -> Command.OFF
      else  -> Command.TOGGLE
    }
    val delta = if (command == Command.TOGGLE) -1 else 0
    val p0 = parseCoordinate(tokens[2 + delta])
    val p1 = parseCoordinate(tokens[4 + delta])
    Instruction(p0, p1, command)
  }
}

fun parseCoordinate(input: String): Pair<Int, Int> {
  return input.split(",").map { it.toInt() }.let { it[0] to it [1] }
}
