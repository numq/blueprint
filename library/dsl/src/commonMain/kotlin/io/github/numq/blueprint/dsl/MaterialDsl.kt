package io.github.numq.blueprint.dsl

import io.github.numq.blueprint.dsl.type.asDynamic
import io.github.numq.blueprint.runtime.component.MaterialPayload
import io.github.numq.blueprint.runtime.component.material.*
import io.github.numq.blueprint.runtime.type.DynamicBool
import io.github.numq.blueprint.runtime.type.DynamicFloat
import io.github.numq.blueprint.runtime.type.DynamicString

fun BlueprintDsl.Text(
    content: DynamicString,
    size: TextSize = TextSize.BODY_MEDIUM,
    color: ColorRole = ColorRole.ON_SURFACE,
    colorHex: String? = null,
    align: TextAlign = TextAlign.START,
    maxLines: Int = Int.MAX_VALUE,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(
        payload = MaterialPayload.Text(content, size, color, colorHex, align, maxLines),
        key = key,
        modifiers = modifiers
    )
}

fun BlueprintDsl.Text(
    content: String,
    size: TextSize = TextSize.BODY_MEDIUM,
    color: ColorRole = ColorRole.ON_SURFACE,
    colorHex: String? = null,
    align: TextAlign = TextAlign.START,
    maxLines: Int = Int.MAX_VALUE,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) = Text(content.asDynamic, size, color, colorHex, align, maxLines, key, modifiers)

fun BlueprintDsl.Button(
    text: DynamicString,
    variant: ButtonVariant = ButtonVariant.FILLED,
    enabled: DynamicBool = true.asDynamic,
    onClickIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(payload = MaterialPayload.Button(text, variant, enabled, onClickIntentId), key = key, modifiers = modifiers)
}

fun BlueprintDsl.Button(
    text: String,
    variant: ButtonVariant = ButtonVariant.FILLED,
    enabled: Boolean = true,
    onClickIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) = Button(text.asDynamic, variant, enabled.asDynamic, onClickIntentId, key, modifiers)

fun BlueprintDsl.Card(
    variant: CardVariant = CardVariant.ELEVATED,
    onClickIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {},
    children: BlueprintDsl.() -> Unit
) {
    node(
        payload = MaterialPayload.Card(variant, onClickIntentId), key = key, modifiers = modifiers, children = children
    )
}

fun BlueprintDsl.Icon(
    name: String,
    tint: ColorRole = ColorRole.ON_SURFACE,
    size: IconSize = IconSize.MEDIUM,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(payload = MaterialPayload.Icon(name, tint, size), key = key, modifiers = modifiers)
}

fun BlueprintDsl.TextField(
    value: DynamicString,
    placeholder: DynamicString? = null,
    enabled: DynamicBool = true.asDynamic,
    onChangeIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(
        payload = MaterialPayload.TextField(value, placeholder, enabled, onChangeIntentId),
        key = key,
        modifiers = modifiers
    )
}

fun BlueprintDsl.TextField(
    value: String,
    placeholder: String? = null,
    enabled: Boolean = true,
    onChangeIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) = TextField(value.asDynamic, placeholder?.asDynamic, enabled.asDynamic, onChangeIntentId, key, modifiers)

fun BlueprintDsl.Checkbox(
    checked: DynamicBool = false.asDynamic,
    enabled: DynamicBool = true.asDynamic,
    onChangeIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(payload = MaterialPayload.Checkbox(checked, enabled, onChangeIntentId), key = key, modifiers = modifiers)
}

fun BlueprintDsl.Checkbox(
    checked: Boolean = false,
    enabled: Boolean = true,
    onChangeIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) = Checkbox(checked.asDynamic, enabled.asDynamic, onChangeIntentId, key, modifiers)

fun BlueprintDsl.Switch(
    checked: DynamicBool = false.asDynamic,
    enabled: DynamicBool = true.asDynamic,
    onChangeIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(payload = MaterialPayload.Switch(checked, enabled, onChangeIntentId), key = key, modifiers = modifiers)
}

fun BlueprintDsl.Switch(
    checked: Boolean = false,
    enabled: Boolean = true,
    onChangeIntentId: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) = Switch(checked.asDynamic, enabled.asDynamic, onChangeIntentId, key, modifiers)

fun BlueprintDsl.ProgressIndicator(
    isLinear: Boolean = false,
    progress: DynamicFloat? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(payload = MaterialPayload.ProgressIndicator(isLinear, progress), key = key, modifiers = modifiers)
}

fun BlueprintDsl.ProgressIndicator(
    isLinear: Boolean = false, progress: Float, key: String? = null, modifiers: ModifierScope.() -> Unit = {}
) = ProgressIndicator(isLinear, progress.asDynamic, key, modifiers)

fun BlueprintDsl.Image(
    url: DynamicString,
    contentDescription: String? = null,
    key: String? = null,
    modifiers: ModifierScope.() -> Unit = {}
) {
    node(payload = MaterialPayload.Image(url, contentDescription), key = key, modifiers = modifiers)
}

fun BlueprintDsl.Image(
    url: String, contentDescription: String? = null, key: String? = null, modifiers: ModifierScope.() -> Unit = {}
) = Image(url.asDynamic, contentDescription, key, modifiers)