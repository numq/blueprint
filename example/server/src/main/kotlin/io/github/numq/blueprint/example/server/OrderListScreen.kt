package io.github.numq.blueprint.example.server

import io.github.numq.blueprint.dsl.*
import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.component.layout.LayoutAlignment
import io.github.numq.blueprint.runtime.component.layout.LayoutArrangement
import io.github.numq.blueprint.runtime.component.material.CardVariant
import io.github.numq.blueprint.runtime.component.material.ColorRole
import io.github.numq.blueprint.runtime.component.material.TextSize

class OrderListScreen {
    fun create(): Blueprint {
        val orders = OrderRepository.getOrders()

        return blueprint("order_list") {
            metadata(title = "My Orders", description = "Track all your orders in one place")
            state("total_orders" to orders.size.toString())

            root {
                Column(
                    verticalArrangement = LayoutArrangement.START,
                    horizontalAlignment = LayoutAlignment.ALIGN_START,
                    modifiers = { background(Palette.BACKGROUND) }) {
                    Header(activeOrdersCount = orders.size)
                    OrderList(orders)
                }
            }
        }
    }

    private fun BlueprintDsl.Header(activeOrdersCount: Int) {
        Column(
            modifiers = { padding(start = 24f, end = 24f, top = 32f, bottom = 16f) }) {
            Text(
                content = "My Orders", size = TextSize.HEADLINE_LARGE, modifiers = { padding(bottom = 8f) })
            Text(
                content = "$activeOrdersCount active orders",
                size = TextSize.BODY_LARGE,
                color = ColorRole.ON_SURFACE_VARIANT
            )
        }
    }

    private fun BlueprintDsl.OrderList(orders: List<Order>) {
        LazyColumn(
            verticalArrangement = LayoutArrangement.START,
            horizontalAlignment = LayoutAlignment.ALIGN_START,
            contentPadding = 24f,
            modifiers = { weight(1f) }) {
            orders.forEach { order ->
                OrderCard(order)
            }
        }
    }

    private fun BlueprintDsl.OrderCard(order: Order) {
        Card(
            variant = CardVariant.FILLED, onClickIntentId = "navigate_order_detail:${order.id}", modifiers = {
                cornerRadius(20f)
                padding(bottom = 16f)
            }) {
            Row(
                horizontalArrangement = LayoutArrangement.SPACE_BETWEEN,
                verticalAlignment = LayoutAlignment.ALIGN_CENTER,
                modifiers = { padding(all = 20f) }) {
                Column(
                    modifiers = {
                        weight(1f)
                        padding(end = 16f)
                    }) {
                    Row(
                        horizontalArrangement = LayoutArrangement.START,
                        verticalAlignment = LayoutAlignment.ALIGN_CENTER,
                        modifiers = { padding(bottom = 8f) }) {
                        Text(
                            content = order.name, size = TextSize.TITLE_LARGE, modifiers = { weight(1f) })
                        StatusBadge(order.status)
                    }
                    Text(
                        content = order.details,
                        size = TextSize.BODY_MEDIUM,
                        color = ColorRole.ON_SURFACE_VARIANT,
                        maxLines = 2
                    )
                }
                ArrowIcon()
            }
        }
    }

    private fun BlueprintDsl.StatusBadge(status: String) {
        Row(
            modifiers = {
                padding(start = 8f)
                background(getStatusBackgroundColor(status))
                cornerRadius(8f)
                padding(start = 12f, end = 12f, top = 6f, bottom = 6f)
            }) {
            Text(
                content = status.uppercase(), size = TextSize.LABEL_SMALL, color = getStatusTextColorRole(status)
            )
        }
    }

    private fun BlueprintDsl.ArrowIcon() {
        Row(
            modifiers = {
                size(width = 40f, height = 40f)
                background(Palette.SURFACE_LIGHT)
                cornerRadius(20f)
            }, horizontalArrangement = LayoutArrangement.CENTER, verticalAlignment = LayoutAlignment.ALIGN_CENTER
        ) {
            Text(content = "→", size = TextSize.TITLE_MEDIUM)
        }
    }

    private fun getStatusBackgroundColor(status: String): String = when (status) {
        "Processing" -> Palette.PROCESSING_BG

        "Shipped" -> Palette.SHIPPED_BG

        "Delivered" -> Palette.DELIVERED_BG

        else -> Palette.DEFAULT_BG
    }

    private fun getStatusTextColorRole(status: String): ColorRole = when (status) {
        "Processing" -> ColorRole.PRIMARY

        "Shipped" -> ColorRole.SECONDARY

        "Delivered" -> ColorRole.ON_SURFACE

        else -> ColorRole.ON_SURFACE
    }

    private object Palette {
        const val BACKGROUND = "#F8F9FA"

        const val SURFACE_LIGHT = "#F0F0F0"

        const val PROCESSING_BG = "#E3F2FD"

        const val SHIPPED_BG = "#F3E5F5"

        const val DELIVERED_BG = "#E8F5E9"

        const val DEFAULT_BG = "#F5F5F5"
    }
}