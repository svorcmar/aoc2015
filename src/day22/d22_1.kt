val initMana = 500
val initHp = 50
val initArmor = 0

val missileMana = 53
val missileDmg = 4

fun main() {
  val input = """Hit Points: 51
Damage: 9"""
  val bossStats = input.lines().map { it.split(" ").last().toInt() }.let { it[0] to it[1] }
  val initState = State(initHp, initArmor, initMana, bossStats.first, bossStats.second, 0, 0, 0)
  val queue = java.util.PriorityQueue<State>()
  queue.add(initState)
  while (!queue.isEmpty()) {
    val state = queue.poll()
    // todo: never enqueue hp < 0
    if (state.bossHp <= 0) {
      println(state.manaSpent)
      break
    }
    // Magic Missile
    if (state.mana >= missileMana) {
      val missile = state.copy(mana = state.mana - missileMana + state.recharge(),
                               bossHp = bossHp - missileDmg - state.).also { it.manaSpent += missileMana }.bossTurn()
    }
  }
}



data class State(val hp: Int, val armor: Int, val mana: Int,
                 val bossHp: Int, val bossDmg: Int,
                 val shieldTimer: Int, val poisonTimer: Int, val rechargeTimer: Int): Comparable<State> {
  
  var manaSpent = 0
    
  override fun compareTo(other: State): Int = manaSpent.compareTo(other.manaSpent)
  
  fun 
  
  fun bossTurn(): State =
}

