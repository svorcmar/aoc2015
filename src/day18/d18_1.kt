typealias Board = Array<Array<Char>>

fun main() {
  val input = ""
  val board = sequence {
    var board = parseBoard(input)
    
    while(true) {
      yield(board)
      board = step(board)
    }
  }
  println(board.take(100 + 1).last().map { r -> r.count { it == '#' } }.sum())
}

fun parseBoard(s: String): Board {
  val lines = s.lines()
  val board = Array(lines.size + 2) { Array(lines[0].length + 2) { '.' } }
  for (i in 0 until lines.size) {
    for (j in 0 until lines[i].length) {
      board[i + 1][j + 1] = lines[i][j]
    }
  }
  return board
}

val diffs = listOf(0 to 1, 0 to -1, 1 to 1, 1 to 0, 1 to -1, -1 to 1, -1 to 0, -1 to -1)

fun step(b: Board): Board {
  val newBoard = Array(b.size) { i -> Array(b[i].size) { '.' } }
  for (i in 1 until b.size - 1) {
    for (j in 1 until b[i].size - 1) {
      newBoard[i][j] = when(diffs.map { b[i + it.first][j + it.second] }.count { it == '#' }) {
        3    -> '#'
        2    -> if (b[i][j] == '#') '#' else '.'
        else -> '.'
      }
    }
  }
  return newBoard
}
