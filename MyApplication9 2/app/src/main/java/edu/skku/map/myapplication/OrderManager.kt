package edu.skku.map.myapplication

object OrderManager {
    private val orders = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orders.add(order)
    }

    fun getOrders(): List<Order> {
        return orders
    }
}

data class Order(
    val drinkName: String,
    val price: Double
)
