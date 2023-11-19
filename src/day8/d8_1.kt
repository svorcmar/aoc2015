fun main() {
  val input = ""
  println(input.lines().map { it.length - unescape(it).length }.sum())
}

enum class State { NORMAL, ESCAPE, HEX1, HEX2 }
val hexChars = "0123456789abcdef"

fun unescape(string: String): String {
  var state = State.NORMAL
  val buffer = StringBuilder()
  for (i in 1 until string.length - 1) {
    state = if (string[i] == '\\') {
      when(state) {
        State.ESCAPE -> {
          buffer.append('\\')
          State.NORMAL
        }
        State.NORMAL -> {
          State.ESCAPE
        }
        else -> throw IllegalStateException("Invalid escape character at index $i")
      }
    } else {
      when(state) {
        State.ESCAPE -> {
          if (string[i] == '"') {
            buffer.append('"')
            State.NORMAL
          } else if (string[i] == 'x') {
            State.HEX1
          } else {
            throw IllegalStateException("Invalid escape sequence at $i")  
          }
        }
        State.HEX1 -> {
          if (!hexChars.contains(string[i]))
            throw IllegalStateException("Invalid non-hex character sequence at $i")
          State.HEX2
        }
        State.HEX2 -> {
          if (!hexChars.contains(string[i]))
            throw IllegalStateException("Invalid non-hex character sequence at $i")
          buffer.append('?')
          State.NORMAL
        }
        State.NORMAL -> {
          buffer.append(string[i])
          State.NORMAL
        }
      }
    }
  }
  return buffer.toString()
}
