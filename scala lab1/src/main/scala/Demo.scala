
object Demo {
  def main(args: Array[String]) {
    var order = new Order(1, "2013-10-01 00:00:00.00",100, "COMPLETE")

    println(order.orderId)
    order.orderId = 2
    println(order.orderId)

  }
}