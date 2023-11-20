fun main() {
  val input = """Sugar: capacity 3, durability 0, flavor 0, texture -3, calories 2
Sprinkles: capacity -3, durability 3, flavor 0, texture 0, calories 9
Candy: capacity -1, durability 0, flavor 4, texture 0, calories 1
Chocolate: capacity 0, durability 0, flavor -2, texture 2, calories 8"""
  val ingredients = input.lines().map { parseIngredient(it) }.associateBy { it.name }
  /*forAllCombinations(ingredients, 100) { it.map { (k, v) -> listOf(
    ingredients[k]!!.capacity * v,
    ingredients[k]!!.durability * v,
    ingredients[k]!!.flavor * v,
    ingredients[k]!!.texture * v) }.fold(listOf(0, 0, 0, 0) { acc, l -> acc.zip(l) { a, b -> a + b } } ) } */
  println(ingredients)
  forAllCombinations(ingredients, 100) { }.forEach{ println }
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
  if (step == stepCount) 
    yield(f(arr.mapIndexed {i, cnt -> names[i] to cnt }.toMap()))
  if (currIdx < arr.size - 1) {
    arr[currIdx + 1]++
    yieldAll(forAllCombinationsRec(step + 1, stepCount, currIdx + 1, arr, names, f))
    arr[currIdx + 1]--
  }
  arr[currIdx]++
  yieldAll(forAllCombinationsRec(step + 1, stepCount, currIdx, arr, names, f))
  arr[currIdx]--
}
  
