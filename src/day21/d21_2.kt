import kotlin.math.*

val weapons = listOf(
  Item("Dagger",      8, 4, 0),
  Item("Shortsword", 10, 5, 0),
  Item("Warhammer",  25, 6, 0),
  Item("Longsword",  40, 7, 0),
  Item("Greataxe",   74, 8, 0)
)

val armors = listOf(
  Item("Leather",    13, 0, 1),
  Item("Chainmail",  31, 0, 2),
  Item("Splintmail", 53, 0, 3),
  Item("Bandedmail", 75, 0, 4),
  Item("Platemail", 102, 0, 5)
) + Item("No armor", 0, 0, 0)

val rings = listOf(
  Item("Damage +1",  25, 1, 0),
  Item("Damage +2",  50, 2, 0),
  Item("Damage +3", 100, 3, 0),
  Item("Defense +1", 20, 0, 1),
  Item("Defense +2", 40, 0, 2),
  Item("Defense +3", 80, 0, 3)
) + Item("No ring", 0, 0, 0) + Item("No ring 2", 0, 0, 0)

data class Item(val label: String, val cost: Int, val damage: Int, val armor: Int)
data class Setup(val cost: Int, val damage: Int, val armor: Int)

fun main() {
  val hp = 100
  val input = ""
  val (bossHp, bossDmg, bossArmor) = input.lines().map { it.split(" ").last().toInt() }.let { Triple(it[0], it[1], it[2]) }
  val setups = mutableListOf<Setup>()
  for (weapon in weapons) {
    for (armor in armors) {
      for (ring1 in rings) {
        for (ring2 in rings) {
          if (ring1 != ring2) {
            setups.add(Setup(
              weapon.cost + armor.cost + ring1.cost + ring2.cost,
              weapon.damage + armor.damage + ring1.damage + ring2.damage,  
              weapon.armor + armor.armor + ring1.armor + ring2.armor,  
            ))
          }
        }
      }
    }
  }
  setups.sortBy { -it.cost }
  for (setup in setups) {
    val playerTurns = ceil((hp.toDouble() / max(bossDmg - setup.armor, 1))).toInt()
    val bossTurns = ceil((bossHp.toDouble() / max(setup.damage - bossArmor, 1))).toInt()
    if (playerTurns < bossTurns) {
      println(setup.cost)
      break
    }
  }
}
