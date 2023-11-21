fun main() {
  val input = ""
  val tickerTape = mapOf(
    "children"    to 3,
    "cats"        to 7,
    "samoyeds"    to 2,
    "pomeranians" to 3,
    "akitas"      to 0,
    "vizslas"     to 0,
    "goldfish"    to 5,
    "trees"       to 3,
    "cars"        to 2,
    "perfumes"    to 1
  )
  println(input.lines().map { parseAunt(it + ",") }.filter { it.matches(tickerTape) }.first().index)
}

fun parseAunt(s: String): Aunt {
  return s.split(" ").let { 
    Aunt(it[1].dropLast(1).toInt(), 
         it.drop(2).chunked(2).map { p -> p[0].dropLast(1) to p[1].dropLast(1).toInt() }.toMap()) }
}
  
data class Aunt(val index: Int, val props: Map<String, Int>) {
    
  fun matches(tickerTape: Map<String, Int>): Boolean =
    tickerTape.all { (k, prop) -> (props[k] ?: prop) == prop }
    
}
