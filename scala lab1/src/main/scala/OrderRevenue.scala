import scala.io.Source

object OrderRevenue {
  def main(args: Array[String]): Unit = {
    val orderItems = Source.fromFile("part-00000").getLines.toList

    val orderItemsFilter = orderItems.filter(orderItem => orderItem.split(",")(1).toInt == 2)

    val orderItemsMap = orderItemsFilter.map(orderItem => orderItem.split(",")(4).toFloat)

    val pricesSum = orderItemsMap.reduce((total, element) => total + element)

    println("Sum of the prices for product 2 is: " + pricesSum)
  }
}
