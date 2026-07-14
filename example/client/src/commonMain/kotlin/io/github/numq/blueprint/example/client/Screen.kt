package io.github.numq.blueprint.example.client

sealed interface Screen {
    data object OrderList : Screen

    data class OrderDetail(val orderId: String) : Screen

    data class Tracking(val orderId: String) : Screen
}