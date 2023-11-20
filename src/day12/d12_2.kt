fun main() {
  val input = ""
  val obj = parseObj(input, 1, false)
  println(obj.sum())
}

fun parseObj(input: String, from: Int, isArray: Boolean): ObjectFragment {
  val sb = StringBuilder()
  val arrSb = StringBuilder()
  val subObjects = mutableListOf<ObjectFragment>()
  var i = from
  while(true) {
    i += when(input[i]) {
      '['  -> {
        subObjects.add(parseObj(input, i + 1, true))
        subObjects.last().length
      }
      ']'  -> {
        if (isArray)
          return ObjectFragment(subObjects, sb.insert(0, '[').append(']').toString(), true)
        else {
          sb.append(input[i])
          1
        }
      }
      '{'  -> {
        subObjects.add(parseObj(input, i + 1, false))
        subObjects.last().length
      }
      '}'  -> {
        if (!isArray)
          return ObjectFragment(subObjects, sb.insert(0, '{').append('}').toString(), false)
        else {
          sb.append(input[i])
          1
        }
      }
      else -> {
        sb.append(input[i])
        1
      }
    }
  }
}

data class ObjectFragment(val subObjects: List<ObjectFragment>, val remainder: String, val isArray: Boolean) {
    
  val length: Int by lazy { subObjects.map { it.length }.sum() + remainder.length }
  
  fun sum(): Int {
    return if (!isArray && remainder.contains("\"red\""))
      0
    else
      sumRemainder() + subObjects.map { it.sum() }.sum()
  }
  
  private fun sumRemainder(): Int =
    "(-?[0-9]+)".toRegex().findAll(remainder).map { it.groupValues[1].toInt() }.sum()
}
