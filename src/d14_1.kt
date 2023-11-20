fun main() {
  val input = ""
  val reindeers = input.lines().map { parseReindeer(it) }.map { Reindeer(it) }
  (1 .. 2503).forEach { reindeers.forEach { it.tick() } }
  println(reindeers.map { it.distance }.max())
}

data class ReindeerInput(val name: String, val speed: Int, val flyTime: Int, val restTime: Int)
fun parseReindeer(s: String): ReindeerInput =
  s.split(" ").let { ReindeerInput(it[0], it[3].toInt(), it[6].toInt(), it[13].toInt()) }
  
class Reindeer(val input: ReindeerInput) {
  var flyTime = 0
  var restTime = 0
  var distance = 0

  fun tick() {
    if (flyTime >= 0) {
      distance += input.speed
      if (++flyTime == input.flyTime) {
        flyTime = -1
      }
    } else {
      if (++restTime == input.restTime) {
        flyTime = 0
        restTime = 0
      }
    }
  }
}
