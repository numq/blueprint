package io.github.numq.blueprint.example.server

object OrderRepository {
    private val orders = listOf(
        Order("1", "Order #1", "Processing", "In transit to local facility"),
        Order("2", "Order #2", "Shipped", "Out for delivery"),
        Order("3", "Order #3", "Delivered", "Left at the door"),
        Order("4", "Order #4", "Processing", "Payment confirmed"),
        Order("5", "Order #5", "Shipped", "Arriving tomorrow")
    )

    fun getOrders(): List<Order> = orders

    fun getOrderById(id: String): Order? = orders.find { it.id == id }
}