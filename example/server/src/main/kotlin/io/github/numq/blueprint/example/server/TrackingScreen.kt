package io.github.numq.blueprint.example.server

import io.github.numq.blueprint.dsl.*
import io.github.numq.blueprint.runtime.Blueprint
import io.github.numq.blueprint.runtime.component.layout.LayoutAlignment
import io.github.numq.blueprint.runtime.component.layout.LayoutArrangement
import io.github.numq.blueprint.runtime.component.material.ButtonVariant
import io.github.numq.blueprint.runtime.component.material.CardVariant
import io.github.numq.blueprint.runtime.component.material.ColorRole
import io.github.numq.blueprint.runtime.component.material.TextSize

class TrackingScreen {
    fun create(orderId: String): Blueprint {
        val order = OrderRepository.getOrderById(orderId) ?: return errorScreen()

        return blueprint("tracking_$orderId") {
            metadata(title = "Track Order", description = "Real-time tracking for your order")

            state("order_id" to orderId, "current_status" to order.status)

            root {
                Column(
                    verticalArrangement = LayoutArrangement.START,
                    horizontalAlignment = LayoutAlignment.ALIGN_START,
                    modifiers = {
                        background(Palette.BACKGROUND)
                        fillMaxSize()
                    }) {
                    TopBar()

                    Column(modifiers = { padding(all = 20f); fillMaxWidth() }) {
                        Header(order.name)

                        Row(
                            horizontalArrangement = LayoutArrangement.START,
                            verticalAlignment = LayoutAlignment.ALIGN_START,
                            modifiers = { fillMaxWidth() }) {
                            Column(modifiers = { weight(0.4f) }) {
                                TimelineCard(order)
                            }

                            Spacer(size = 16f)

                            Column(modifiers = { weight(0.6f) }) {
                                StatusCard(order)
                                Spacer(size = 16f)
                                SupportButton(order.id)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun BlueprintDsl.TopBar() {
        Button(
            text = "← Back", variant = ButtonVariant.TEXT, onClickIntentId = "navigate_back", modifiers = {
                padding(start = 8f, top = 8f)
                alpha(0.8f)
            })
    }

    private fun BlueprintDsl.Header(orderName: String) {
        Text(
            content = "📍 Track Order", size = TextSize.HEADLINE_LARGE, modifiers = { padding(bottom = 4f) })
        Text(
            content = orderName,
            size = TextSize.TITLE_MEDIUM,
            color = ColorRole.ON_SURFACE_VARIANT,
            modifiers = { padding(bottom = 24f) })
    }

    private fun BlueprintDsl.TimelineCard(order: Order) {
        Card(
            variant = CardVariant.ELEVATED, modifiers = {
                elevation(4f)
                cornerRadius(20f)
                padding(bottom = 16f)
            }) {
            Column(modifiers = { padding(all = 24f) }) {
                Text(
                    content = "Tracking Progress", size = TextSize.TITLE_SMALL, modifiers = { padding(bottom = 16f) })

                val steps = getTimelineSteps(order.status)

                steps.forEachIndexed { index, step ->
                    TimelineStep(
                        label = step.label,
                        isCompleted = step.isCompleted,
                        isLast = index == steps.lastIndex,
                        status = order.status
                    )
                }
            }
        }
    }

    private fun BlueprintDsl.TimelineStep(label: String, isCompleted: Boolean, isLast: Boolean, status: String) {
        Row(
            horizontalArrangement = LayoutArrangement.START,
            verticalAlignment = LayoutAlignment.ALIGN_CENTER,
            modifiers = {
                padding(bottom = if (!isLast) 12f else 0f)
                alpha(if (isCompleted) 1f else 0.5f)
            }) {
            Row(
                horizontalArrangement = LayoutArrangement.CENTER,
                verticalAlignment = LayoutAlignment.ALIGN_CENTER,
                modifiers = {
                    size(width = 24f, height = 24f)
                    background(getStepColor(isCompleted, status))
                    cornerRadius(12f)
                }) {
                Text(
                    content = if (isCompleted) "✓" else "•",
                    colorHex = if (isCompleted) Palette.WHITE else Palette.GRAY,
                    size = TextSize.BODY_SMALL
                )
            }

            Text(
                content = label,
                size = TextSize.BODY_MEDIUM,
                color = if (isCompleted) ColorRole.ON_SURFACE else ColorRole.ON_SURFACE_VARIANT,
                modifiers = { padding(start = 16f) })

            if (!isCompleted && !isLast) {
                Text(
                    content = "...",
                    size = TextSize.BODY_SMALL,
                    color = ColorRole.ON_SURFACE_VARIANT,
                    modifiers = { padding(start = 8f) })
            }
        }

        if (!isLast) {
            Row(
                modifiers = {
                    padding(start = 12f, top = 4f, bottom = 4f)
                    size(width = 2f, height = 16f)
                    background(if (isCompleted) Palette.PRIMARY else Palette.LIGHT_GRAY)
                }) { }
        }
    }

    private fun BlueprintDsl.StatusCard(order: Order) {
        Card(
            variant = CardVariant.FILLED, modifiers = {
                padding(top = 16f)
                cornerRadius(16f)
                fillMaxWidth()
            }) {
            Row(
                horizontalArrangement = LayoutArrangement.START,
                verticalAlignment = LayoutAlignment.ALIGN_START,
                modifiers = { padding(all = 20f) }) {
                Column(modifiers = {
                    weight(1f)
                }) {
                    Text(
                        content = "Current Status",
                        size = TextSize.LABEL_SMALL,
                        color = ColorRole.ON_SURFACE_VARIANT,
                        modifiers = { padding(bottom = 4f) })
                    Text(
                        content = order.status,
                        size = TextSize.BODY_LARGE,
                        color = getStatusColorRole(order.status),
                        modifiers = { fillMaxWidth() })
                    Spacer(size = 4f)
                    Text(
                        content = order.details,
                        size = TextSize.BODY_SMALL,
                        color = ColorRole.ON_SURFACE_VARIANT,
                        maxLines = 3,
                        modifiers = { fillMaxWidth() })
                }
            }
        }
    }

    private fun BlueprintDsl.SupportButton(orderId: String) {
        Button(
            text = "Contact Support",
            variant = ButtonVariant.OUTLINED,
            onClickIntentId = "contact_support:$orderId",
            modifiers = {
                fillMaxWidth()
                height(48f)
                alpha(.6f)
            })
    }

    private fun errorScreen(): Blueprint = blueprint("error") {
        metadata(title = "Error", description = "Tracking information not available")

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
                        Text(content = "⚠️", size = TextSize.HEADLINE_LARGE)
                    }

                    Spacer(size = 24f)

                    Text(
                        content = "Tracking Unavailable",
                        size = TextSize.HEADLINE_MEDIUM,
                        color = ColorRole.ERROR,
                        modifiers = { padding(bottom = 8f) })

                    Text(
                        content = "Unable to retrieve tracking information",
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

    private data class Step(val label: String, val isCompleted: Boolean)

    private fun getTimelineSteps(status: String) = listOf(
        Step("Order Placed", status != "Processing"),
        Step("Processing", status != "Processing"),
        Step("Shipped", status == "Shipped" || status == "Delivered"),
        Step("Delivered", status == "Delivered")
    )

    private fun getStepColor(isCompleted: Boolean, status: String): String {
        if (!isCompleted) return Palette.LIGHT_GRAY

        return if (status == "Delivered") Palette.SUCCESS else Palette.PRIMARY
    }

    private fun getStatusColorRole(status: String): ColorRole = when (status) {
        "Processing" -> ColorRole.PRIMARY

        "Shipped" -> ColorRole.SECONDARY

        else -> ColorRole.ON_SURFACE
    }

    private object Palette {
        const val BACKGROUND = "#F8F9FA"

        const val PRIMARY = "#2196F3"

        const val SUCCESS = "#4CAF50"

        const val GRAY = "#9E9E9E"

        const val LIGHT_GRAY = "#E0E0E0"

        const val WHITE = "#FFFFFF"

        const val ERROR_BG = "#FEE2E2"
    }
}