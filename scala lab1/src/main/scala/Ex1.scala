
object Ex1 {
  def main(args: Array[String]): Unit = {
    // Question a:
    println(sum(1, 3))
    println(sumSquares(1, 3))
    println(sumCubes(1, 3))
    println(sumDoubles(1, 3))

    // Question b:
    println(sum2(id, 1, 3))
    println(sum2(square, 1, 3))
    println(sum2(cube, 1, 3))
    println(sum2(double, 1, 3))
  }

  // Question a:
  def sum(lb: Int, ub: Int): Int = {
    var total = 0
    for (element <- lb to ub) {
      total += element
    }
    total
  }

  def sumSquares(lb: Int, ub: Int) = {
    var total = 0
    for (element <- lb to ub) {
      total += element * element
    }
    total
  }

  def sumCubes(lb: Int, ub: Int) = {
    var total = 0
    for (element <- lb to ub) {
      total += element * element * element
    }
    total
  }

  def sumDoubles(lb: Int, ub: Int) = {
    var total = 0
    for (element <- lb to ub) {
      total += element * 2
    }
    total
  }

  // Question b:
  def sum2(fn: Int => Int, lb: Int, ub: Int): Int = {
    var total = 0
    for (element <- lb to ub) {
      total += fn(element)
    }
    total
  }

  def id(i: Int): Int = i
  def square(i: Int): Int = i * i
  def cube(i: Int): Int = square(i) * i
  def double(i: Int): Int = i * 2
}
