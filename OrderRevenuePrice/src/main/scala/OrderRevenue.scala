import scala.io.Source

object OrderRevenue {
  def main(args: Array[String]): Unit = {
    val orderId = args(1).toInt

    val orderItems = Source.fromFile("part-00000").getLines

    val orderItemsFilter = orderItems.filter(orderItem => orderItem.split(",")(1).toInt == orderId)

    val orderItemsMap = orderItemsFilter.map(orderItem => orderItem.split(",")(4).toFloat)

    val pricesSum = orderItemsMap.reduce((total, element) => total + element)

    println("Sum of the prices for order " + orderId + " is: " + pricesSum)
  }
}
