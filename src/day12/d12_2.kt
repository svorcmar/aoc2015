fun main() {
  val input = ""
  val obj = parseObj(input, 1)
  println(obj.sum())
}

fun parseObj(input: String, from: Int): ObjectFragment {
  val sb = StringBuilder()
  val arrSb = StringBuilder()
  val subObjects = mutableListOf<ObjectFragment>()
  var i = from
  while(true) {
    i += when(input[i]) {
      '['  -> TODO()
      ']'  -> TODO()
      '{'  -> {
        subObjects.add(parseObj(input, i + 1))
        subObjects.last().length
      }
      '}'  -> return ObjectFragment(subObjects, sb.insert(0, '{').append('}').toString()).also { println(it.remainder) }
      else -> {
        sb.append(input[i])
        1
      }
    }
  }
}

data class ObjectFragment(val subObjects: List<ObjectFragment>, val remainder: String, val ignoredRemainder: String) {
    
  val length: Int by lazy { subObjects.map { it.length }.sum() + remainder.length }
  
  fun sum(): Int {
    return if (remainder.contains("\"red\""))
      0
    else
      sumString(remainder) + sumString(ignoredRemainder) + subObjects.map { it.sum() }.sum()
  }
  
  private fun sumString(s: String): Int =
    "(-?[0-9]+)".toRegex().findAll(s).map { it.groupValues[1].toInt() }.sum()
}
