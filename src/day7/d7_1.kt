fun main() {
  val input = ""
  val byTarget = input.lines().map { parseConnection(it) }.associate { it.target to it }
  val cache = mutableMapOf<String, UShort>()
  println(eval("a", byTarget, cache))
}

enum class Gate { WIRE, NOT, AND, OR, LSHIFT, RSHIFT }
data class Connection(val gate: Gate, val leftOp: String, val rightOp: String, val target: String)

fun parseConnection(input: String): Connection {
  val lr = input.split(" -> ")
  val target = lr[1]
  val l = lr[0].split(" ")
  return when (l.size) {
    1    -> Connection(Gate.WIRE, l[0], "", target)
    2    -> Connection(Gate.NOT, l[1], "", target)
    else -> Connection(Gate.valueOf(l[1]), l[0], l[2], target)
  }
}

fun eval(wire: String, connections: Map<String, Connection>, cache: MutableMap<String, UShort>): UShort {
  if (!cache.containsKey(wire))
    cache[wire] = evalConnection(connections[wire]!!, connections, cache)
  return cache[wire]!!
}

fun evalConnection(connection: Connection, connections: Map<String, Connection>, cache: MutableMap<String, UShort>): UShort {
  return when(connection.gate) {
    Gate.WIRE   -> evalOperand(connection.leftOp, connections, cache)
    Gate.NOT    -> evalOperand(connection.leftOp, connections, cache).inv()
    Gate.AND    -> evalOperand(connection.leftOp, connections, cache) and evalOperand(connection.rightOp, connections, cache)
    Gate.OR     -> evalOperand(connection.leftOp, connections, cache) or evalOperand(connection.rightOp, connections, cache)
    Gate.LSHIFT -> (evalOperand(connection.leftOp, connections, cache).toInt() shl evalOperand(connection.rightOp, connections, cache).toInt()).toUShort()
    Gate.RSHIFT -> (evalOperand(connection.leftOp, connections, cache).toInt() shr evalOperand(connection.rightOp, connections, cache).toInt()).toUShort()
  }
}

fun evalOperand(operand: String, connections: Map<String, Connection>, cache: MutableMap<String, UShort>): UShort {
  if (operand.matches("[0-9]+".toRegex()))
    return operand.toUShort()
  return eval(operand, connections, cache)
}
