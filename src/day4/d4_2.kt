fun main() {
  val input = "ckczppom"
  var solution = -1
  generateSequence(0) { it + 1 }.takeWhile { solution == -1 }.map { 
    val md5 = java.security.MessageDigest.getInstance("md5")
      .digest((input + it).toByteArray())
      .joinToString("") { "%02x".format(it) }
    if (md5.startsWith("000000"))
      solution = it
  }.last()
  println(solution)
}
