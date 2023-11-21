fun main() {
  val input = ""
  val ingredients = input.lines().map { parseIngredient(it) }.associateBy { it.name }
  println(forAllCombinations(ingredients, 100) { 
    it.map { (k, v) -> listOf(
      ingredients[k]!!.capacity * v,
      ingredients[k]!!.durability * v,
      ingredients[k]!!.flavor * v,
      ingredients[k]!!.texture * v)
    }.fold(listOf(0, 0, 0, 0)) { acc, list -> acc.zip(list) { a, b -> a + b } }
     .map { if (it < 0) 0 else it }
     .reduce(Int::times) 
  }.max())
}

data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int)
fun parseIngredient(s: String): Ingredient =
  s.split(" ").let { Ingredient(it[0].dropLast(1), 
                                it[2].dropLast(1).toInt(), 
                                it[4].dropLast(1).toInt(), 
                                it[6].dropLast(1).toInt(), 
                                it[8].dropLast(1).toInt()) }

fun <T> forAllCombinations(ingredients: Map<String, Ingredient>, n: Int, f: (Map<String, Int>) -> T): Sequence<T> {
  val names = ingredients.keys.toList()
  val arr = Array(names.size) { 0 }
  return forAllCombinationsRec(0, n, 0, arr, names, f)
}

fun <T> forAllCombinationsRec(step: Int, stepCount: Int, currIdx: Int,
                              arr: Array<Int>, names: List<String>,
                              f: (Map<String, Int>) -> T): Sequence<T> = sequence {
  if (step == stepCount) {
    yield(f(arr.mapIndexed {i, cnt -> names[i] to cnt }.toMap()))
  } else {
    if (currIdx < arr.size - 1) {
      arr[currIdx + 1]++
      yieldAll(forAllCombinationsRec(step + 1, stepCount, currIdx + 1, arr, names, f))
      arr[currIdx + 1]--
    }
    arr[currIdx]++
    yieldAll(forAllCombinationsRec(step + 1, stepCount, currIdx, arr, names, f))
    arr[currIdx]--
  }
}
  
