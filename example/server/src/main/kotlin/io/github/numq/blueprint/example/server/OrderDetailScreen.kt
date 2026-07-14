package io.github.numq.blueprint.example.server

import io.github.numq.blueprint.dsl.*
import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.component.layout.LayoutAlignment
import io.github.numq.blueprint.runtime.component.layout.LayoutArrangement
import io.github.numq.blueprint.runtime.component.material.ButtonVariant
import io.github.numq.blueprint.runtime.component.material.CardVariant
import io.github.numq.blueprint.runtime.component.material.ColorRole
import io.github.numq.blueprint.runtime.component.material.TextSize

class OrderDetailScreen {
    fun create(orderId: String): Blueprint {
        val order = OrderRepository.getOrderById(orderId) ?: return errorScreen(orderId)

        return blueprint("order_detail_$orderId") {
            metadata(title = "Order Details", description = "Complete information about your order")

            state("order_id" to orderId, "order_status" to order.status)

            root {
                Column(
                    verticalArrangement = LayoutArrangement.START,
                    horizontalAlignment = LayoutAlignment.ALIGN_START,
                    modifiers = { background(Palette.BACKGROUND) }) {
                    TopBar()

                    Column(
                        modifiers = { padding(start = 24f, end = 24f, top = 8f, bottom = 32f) }) {
                        OrderHeader(order, orderId)
                        StatusOverviewCard(order)
                        OrderDetailsCard(order)
                        ActionButtons(orderId)
                    }
                }
            }
        }
    }

    private fun BlueprintDsl.TopBar() {
        Button(
            text = "← Back", variant = ButtonVariant.TEXT, onClickIntentId = "navigate_back", modifiers = {
                padding(start = 8f, top = 8f)
                alpha(.8f)
            })
    }

    private fun BlueprintDsl.OrderHeader(order: Order, orderId: String) {
        Row(
            horizontalArrangement = LayoutArrangement.START,
            verticalAlignment = LayoutAlignment.ALIGN_CENTER,
            modifiers = { padding(bottom = 32f) }) {
            Row(
                horizontalArrangement = LayoutArrangement.CENTER,
                verticalAlignment = LayoutAlignment.ALIGN_CENTER,
                modifiers = {
                    size(width = 64f, height = 64f)
                    background(getStatusBackgroundColor(order.status))
                    cornerRadius(32f)
                }) {
                Text(
                    content = getStatusIcon(order.status), size = TextSize.HEADLINE_MEDIUM
                )
            }

            Column(
                modifiers = {
                    padding(start = 20f)
                    weight(1f)
                }) {
                Text(
                    content = order.name, size = TextSize.HEADLINE_SMALL, modifiers = { padding(bottom = 4f) })
                Text(
                    content = "Order #${orderId.take(8).uppercase()}",
                    size = TextSize.BODY_MEDIUM,
                    color = ColorRole.ON_SURFACE_VARIANT
                )
            }
        }
    }

    private fun BlueprintDsl.StatusOverviewCard(order: Order) {
        Card(
            variant = CardVariant.FILLED, modifiers = {
                padding(bottom = 20f)
                cornerRadius(24f)
            }) {
            Row(
                horizontalArrangement = LayoutArrangement.START,
                verticalAlignment = LayoutAlignment.ALIGN_CENTER,
                modifiers = { padding(all = 24f) }) {
                Column(
                    modifiers = {
                        padding(end = 16f)
                        weight(1f)
                    }) {
                    Text(
                        content = "Current Status",
                        size = TextSize.LABEL_MEDIUM,
                        color = ColorRole.ON_SURFACE_VARIANT,
                        modifiers = { padding(bottom = 4f) })
                    Text(
                        content = order.status,
                        size = TextSize.TITLE_LARGE,
                        color = getStatusTextColorRole(order.status)
                    )
                }

                ProgressIndicator(
                    isLinear = false,
                    progress = getStatusProgress(order.status),
                    modifiers = { size(width = 56f, height = 56f) })
            }
        }
    }

    private fun BlueprintDsl.OrderDetailsCard(order: Order) {
        Card(
            variant = CardVariant.FILLED, modifiers = {
                padding(bottom = 32f)
                cornerRadius(24f)
            }) {
            Column(modifiers = { padding(all = 24f) }) {
                Text(
                    content = "📋 Order Details", size = TextSize.TITLE_MEDIUM, modifiers = { padding(bottom = 12f) })
                Text(
                    content = order.details, size = TextSize.BODY_LARGE, color = ColorRole.ON_SURFACE_VARIANT
                )
            }
        }
    }

    private fun BlueprintDsl.ActionButtons(orderId: String) {
        Column(
            verticalArrangement = LayoutArrangement.CENTER,
            horizontalAlignment = LayoutAlignment.ALIGN_CENTER,
            modifiers = {
                weight(1f)
                fillMaxWidth()
            }) {
            Button(
                text = "📍 Track Order",
                variant = ButtonVariant.FILLED,
                onClickIntentId = "navigate_tracking:$orderId",
                modifiers = {
                    fillMaxWidth()
                    height(56f)
                    cornerRadius(28f)
                    padding(bottom = 12f)
                })

            Button(
                text = "Contact Support",
                variant = ButtonVariant.TEXT,
                onClickIntentId = "contact_support:$orderId",
                modifiers = {
                    fillMaxWidth()
                    height(48f)
                    alpha(.7f)
                })
        }
    }

    private fun errorScreen(orderId: String): Blueprint = blueprint("error_$orderId") {
        metadata(title = "Error", description = "Order not found")

        root {
            Column(
                verticalArrangement = LayoutArrangement.CENTER,
                horizontalAlignment = LayoutAlignment.ALIGN_CENTER,
                modifiers = { background(Palette.BACKGROUND) }) {
                Column(
                    verticalArrangement = LayoutArrangement.CENTER,
                    horizontalAlignment = LayoutAlignment.ALIGN_CENTER,
                    modifiers = { padding(all = 32f) }) {
                    Row(
                        horizontalArrangement = LayoutArrangement.CENTER,
                        verticalAlignment = LayoutAlignment.ALIGN_CENTER,
                        modifiers = {
                            size(width = 80f, height = 80f)
                            background(Palette.ERROR_BG)
                            cornerRadius(40f)
                            padding(all = 20f)
                        }) {
                        Text(content = "🔍", size = TextSize.HEADLINE_LARGE)
                    }

                    Spacer(size = 24f)

                    Text(
                        content = "Order Not Found",
                        size = TextSize.HEADLINE_MEDIUM,
                        color = ColorRole.ERROR,
                        modifiers = { padding(bottom = 8f) })

                    Text(
                        content = "We couldn't find order #$orderId",
                        size = TextSize.BODY_MEDIUM,
                        color = ColorRole.ON_SURFACE_VARIANT,
                        modifiers = { padding(bottom = 32f) })

                    Button(
                        text = "← Back to Orders",
                        variant = ButtonVariant.FILLED,
                        onClickIntentId = "navigate_back",
                        modifiers = {
                            size(width = 200f, height = 48f)
                            cornerRadius(24f)
                            elevation(2f)
                        })
                }
            }
        }
    }

    private fun getStatusBackgroundColor(status: String): String = when (status) {
        "Processing" -> Palette.PROCESSING_BG

        "Shipped" -> Palette.SHIPPED_BG

        "Delivered" -> Palette.DELIVERED_BG

        else -> Palette.DEFAULT_BG
    }

    private fun getStatusIcon(status: String): String = when (status) {
        "Processing" -> "⏳"

        "Shipped" -> "🚚"

        "Delivered" -> "✅"

        else -> "📦"
    }

    private fun getStatusTextColorRole(status: String): ColorRole = when (status) {
        "Processing" -> ColorRole.PRIMARY

        "Shipped" -> ColorRole.SECONDARY

        "Delivered" -> ColorRole.ON_SURFACE

        else -> ColorRole.ON_SURFACE
    }

    private fun getStatusProgress(status: String): Float = when (status) {
        "Processing" -> .33f

        "Shipped" -> .66f

        "Delivered" -> 1f

        else -> .0f
    }

    private object Palette {
        const val BACKGROUND = "#F8F9FA"

        const val ERROR_BG = "#FEE2E2"

        const val PROCESSING_BG = "#E3F2FD"

        const val SHIPPED_BG = "#F3E5F5"

        const val DELIVERED_BG = "#E8F5E9"

        const val DEFAULT_BG = "#F5F5F5"
    }
}