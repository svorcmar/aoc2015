import java.util.TreeSet

val initMana = 500
val initHp = 50

val missileMana = 53
val missileDmg = 4

val drainMana = 73
val drainDelta = 2

val shieldMana = 113
val shieldTurns = 6
val shieldArmor = 7

val poisonMana = 173
val poisonTurns = 6
val poisonDmg = 3

val rechargeMana = 229
val rechargeTurns = 5
val rechargeRegen = 101


fun main() {
  val input = ""
  val bossStats = input.lines().map { it.split(" ").last().toInt() }.let { it[0] to it[1] }
  val initState = State(initHp, initMana, bossStats.first, bossStats.second, 0, 0, 0).also { it.label = "Initial state" }
  val queue = TreeSet<State>()
  queue.add(initState)
  while (!queue.isEmpty()) {
    val state = queue.pollFirst()
    if (state.bossHp <= 0) {
      println(state.manaSpent)
      break
    }
    if (state.mana >= missileMana) {
      enqueue(state.action("Magic Missile",
                           manaD   = -missileMana,
                           bossHpD = -missileDmg), queue)
    }
    if (state.mana >= drainMana) {
      enqueue(state.action("Drain",
                           hpD     = drainDelta,
                           manaD   = -drainMana,
                           bossHpD = -drainDelta), queue)
    }
    if (state.shieldTimer == 0 && state.mana >= shieldMana) {
      enqueue(state.action("Shield",
                           manaD        = -shieldMana,
                           shieldTimerD = shieldTurns), queue)
    }
    if (state.poisonTimer == 0 && state.mana >= poisonMana) {
      enqueue(state.action("Poison",
                           manaD        = -poisonMana,
                           poisonTimerD = poisonTurns), queue)
    }
    if (state.rechargeTimer == 0 && state.mana >= rechargeMana) {
      enqueue(state.action("Recharge",
                           manaD          = -rechargeMana,
                           rechargeTimerD = rechargeTurns), queue)
    }
  }
}

fun enqueue(newState: State?, queue: TreeSet<State>) {
  if (newState != null) {
    val prevQueued = queue.lower(newState)
    if (prevQueued == null || prevQueued != newState || prevQueued.manaSpent > newState.manaSpent) {
      queue.remove(newState)
      queue.add(newState)
    }
  }
}

data class State(val hp: Int, val mana: Int,
                 val bossHp: Int, val bossDmg: Int,
                 val shieldTimer: Int, val poisonTimer: Int, val rechargeTimer: Int): Comparable<State> {
  
  var manaSpent = 0
  var label: String = ""
  var parent: State? = null
    
  override fun compareTo(other: State): Int = manaSpent.compareTo(other.manaSpent)
  
  fun action(label: String, hpD: Int = 0, manaD: Int = 0, bossHpD: Int = 0,
             shieldTimerD: Int = 0, poisonTimerD: Int = 0, rechargeTimerD: Int = 0): State? {
    // apply action
    var newHp = hp + hpD
    if (newHp <= 0)
      return null
    var newMana = mana + manaD
    var newBossHp = bossHp + bossHpD
    if (newBossHp <= 0)
      return copy(bossHp = 0).initialize(label, manaD, this)
    // apply effects
    var newShieldTimer = shieldTimer + shieldTimerD
    val shield = if (newShieldTimer-- > 0) shieldArmor else 0
    var newPoisonTimer = poisonTimer + poisonTimerD
    newBossHp -= if (newPoisonTimer-- > 0) poisonDmg else 0
    if (newBossHp <= 0)
      return copy(bossHp = 0).initialize(label, manaD, this)
    var newRechargeTimer = rechargeTimer + rechargeTimerD
    newMana += if (newRechargeTimer-- > 0) rechargeRegen else 0
    // apply boss move
    newHp -= Math.max(bossDmg - shield, 0)
    if (newHp <= 0)
      return null
    // apply effects  
    newShieldTimer--
    newBossHp -= if (newPoisonTimer-- > 0) poisonDmg else 0
    if (newBossHp <= 0)
      return copy(bossHp = 0).initialize(label, manaD, this)
    newMana += if (newRechargeTimer-- > 0) rechargeRegen else 0
    return State(newHp, newMana, newBossHp, bossDmg,
                 Math.max(newShieldTimer, 0), Math.max(newPoisonTimer, 0), Math.max(newRechargeTimer, 0)).initialize(label, manaD, this)
  }
  
  fun initialize(label: String, manaDelta: Int, parent: State): State {
    this.label = label  
    this.manaSpent = parent.manaSpent - manaDelta
    this.parent = parent
    return this
  }
  
  fun toStringWithMetadata(): String = "[$label @$manaSpent] ${toString()}"
  
  fun formatPath(): String = parent.let { if (it == null) toStringWithMetadata() else it.formatPath() + " -> " + toStringWithMetadata() }

}
