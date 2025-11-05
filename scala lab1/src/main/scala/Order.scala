class Order(var orderId: Int, var orderDate: String, var orderCustomerId: Int, var orderStatus: String) {
  println("I am in the constructor")

  override def toString: String = "Order(" + orderId + ", " + orderDate + ", " + orderCustomerId + ", " + orderStatus + ")"
}

