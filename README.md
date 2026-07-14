# Blueprint

A Kotlin Multiplatform Server-Driven UI (SDUI) library that enables dynamic, server-controlled user interfaces with
Compose Multiplatform rendering.

## Overview

Blueprint is a comprehensive SDUI framework that separates UI structure from client logic, allowing servers to define
complete user interfaces as structured data. The client renders these blueprints using Compose Multiplatform, creating a
fully dynamic UI experience across platforms.

### Key Features

- **Server-Driven Architecture** — Define your entire UI structure on the server and stream it to clients as structured
  data
- **Type-Safe DSL** — Build blueprints programmatically on the server using a Kotlin DSL with compile-time safety
- **Compose Multiplatform Rendering** — Render server-defined UIs natively on Android, iOS, Desktop, and Web
- **Bidirectional Communication** — Send user interactions (intents) to the server and receive UI updates (state
  patches) in response
- **Dynamic State Binding** — Bind UI components to server-managed state that updates in real-time
- **Material 3 Components** — Built-in support for Material Design 3 components including buttons, cards, text fields,
  and more
- **Layout System** — Full layout primitives including Row, Column, Box, LazyColumn, and LazyRow
- **Navigation Effects** — Server-controlled navigation with push, replace, and pop semantics
- **Pluggable Renderers** — Extend the rendering system with custom components and custom rendering logic
- **Protocol Buffers Support** — Wire protocol definitions for efficient serialization (optional)

## Architecture

Blueprint follows a clean separation of concerns across multiple modules:

| Module             | Description                                                                                |
|--------------------|--------------------------------------------------------------------------------------------|
| `runtime`          | Core data models: `Blueprint`, `BlueprintNode`, `Intent`, `Resolution`, component payloads |
| `dsl`              | Server-side Kotlin DSL for building blueprints programmatically                            |
| `renderer`         | Abstract rendering interfaces and Composable context providers                             |
| `renderer-compose` | Compose Multiplatform renderer implementations for all built-in components                 |
| `protocol`         | Protocol Buffers definitions for efficient serialization (optional)                        |

## Quick Start

### Server Side: Building Blueprints

Use the Kotlin DSL to construct UI blueprints on your server:

```kotlin
import io.github.numq.blueprint.dsl.*

fun createUserProfileScreen(userId: String): Blueprint {
    val user = userRepository.findById(userId) ?: return errorScreen()

    return blueprint("user_profile_$userId") {
        metadata(
            title = "User Profile",
            description = "Profile page for ${user.name}"
        )

        state("user_name" to user.name, "is_verified" to user.verified.toString())

        root {
            Column(
                verticalArrangement = LayoutArrangement.START,
                modifiers = {
                    padding(all = 24f)
                    background("#F8F9FA")
                }
            ) {
                Text(
                    content = "Profile",
                    size = TextSize.HEADLINE_LARGE,
                    modifiers = { padding(bottom = 16f) }
                )

                Text(
                    content = bindString("user_name"),
                    size = TextSize.TITLE_LARGE
                )

                Spacer(size = 24f)

                Button(
                    text = "Edit Profile",
                    variant = ButtonVariant.FILLED,
                    onClickIntentId = "edit_profile:$userId",
                    modifiers = {
                        fillMaxWidth()
                        height(48f)
                        cornerRadius(24f)
                    }
                )
            }
        }
    }
}
```

### Server Side: Handling Intents

```kotlin
post("/action") {
    val intent = call.receive<Intent>()

    val resolution = when {
        intent.id.startsWith("edit_profile:") -> {
            val userId = intent.id.removePrefix("edit_profile:")
            Resolution(
                effects = listOf(
                    Effect.Navigation(
                        screenId = "edit_profile",
                        params = mapOf("id" to userId)
                    )
                )
            )
        }

        intent.id == "delete_account" -> Resolution(
            effects = listOf(
                Effect.Dialog(
                    title = "Confirm Deletion",
                    message = "Are you sure you want to delete your account?"
                ),
                Effect.Snackbar(message = "Account deletion requested")
            )
        )

        else -> Resolution()
    }

    call.respond(resolution)
}
```

### Client Side: Rendering Blueprints

```kotlin
@Composable
fun MyApp(client: HttpClient) {
    var blueprint by remember { mutableStateOf<Blueprint?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val response = client.get("http://localhost:8080/screen/profile/123")
        if (response.status.isSuccess()) {
            blueprint = response.body()
        }
    }

    val intentHandler = remember {
        IntentHandler { intent ->
            scope.launch {
                val response = client.post("http://localhost:8080/action") {
                    contentType(ContentType.Application.Json)
                    setBody(intent)
                }

                if (response.status.isSuccess()) {
                    val resolution = response.body<Resolution>()

                    if (resolution.statePatches.isNotEmpty()) {
                        blueprint = blueprint?.copy(
                            state = blueprint!!.state + resolution.statePatches
                        )
                    }

                    resolution.effects.forEach { effect ->
                        handleEffect(effect)
                    }
                }
            }
        }
    }

    val renderer = remember { createDefaultBlueprintRegistry() }

    MaterialTheme {
        blueprint?.let { bp ->
            renderer.render(
                blueprint = bp,
                intentHandler = intentHandler
            )
        }
    }
}
```

## Component Reference

### Layout Components

| Component    | Description                                   | Key Properties                                          |
|--------------|-----------------------------------------------|---------------------------------------------------------|
| `Box`        | Basic container with single content alignment | `contentAlignment`                                      |
| `Column`     | Vertical linear layout                        | `verticalArrangement`, `horizontalAlignment`, `spacing` |
| `Row`        | Horizontal linear layout                      | `horizontalArrangement`, `verticalAlignment`, `spacing` |
| `LazyColumn` | Vertical scrolling list                       | `contentPadding`, `onLoadMoreIntentId`                  |
| `LazyRow`    | Horizontal scrolling list                     | `contentPadding`, `reversed`                            |
| `Spacer`     | Empty space                                   | `size`                                                  |

### Material Components

| Component           | Description              | Key Properties                                        |
|---------------------|--------------------------|-------------------------------------------------------|
| `Text`              | Text display             | `content`, `size`, `color`, `align`, `maxLines`       |
| `Button`            | Interactive button       | `text`, `variant`, `enabled`, `onClickIntentId`       |
| `Card`              | Container with elevation | `variant`, `onClickIntentId`                          |
| `TextField`         | Text input field         | `value`, `placeholder`, `enabled`, `onChangeIntentId` |
| `Checkbox`          | Boolean toggle           | `checked`, `enabled`, `onChangeIntentId`              |
| `Switch`            | Toggle switch            | `checked`, `enabled`, `onChangeIntentId`              |
| `ProgressIndicator` | Progress display         | `isLinear`, `progress`                                |
| `Image`             | Image display            | `url`, `contentDescription`                           |
| `Icon`              | Icon display             | `name`, `tint`, `size`                                |

### Modifiers

| Modifier                                         | Description                          |
|--------------------------------------------------|--------------------------------------|
| `padding`                                        | Add padding (directional or uniform) |
| `width` / `height`                               | Fixed dimensions                     |
| `size`                                           | Fixed width and height               |
| `background`                                     | Background color (hex string)        |
| `elevation`                                      | Shadow elevation                     |
| `cornerRadius`                                   | Rounded corners                      |
| `alpha`                                          | Opacity (0.0 to 1.0)                 |
| `weight`                                         | Weight in linear layouts             |
| `fillMaxWidth` / `fillMaxHeight` / `fillMaxSize` | Fill available space                 |

## Dynamic State Binding

Blueprint supports binding component properties to server-managed state:

```kotlin
state("greeting" to "Hello, World!", "is_loading" to "true")

Text(content = bindString("greeting"))

Button(enabled = bindBool("is_loading"))
```

When the server returns a `Resolution` with `statePatches`:

```kotlin
Resolution(
    statePatches = mapOf(
        "greeting" to "Updated text!",
        "is_loading" to "false"
    )
)
```

## Effects System

### Navigation Effects

```kotlin
Effect.Navigation(
    screenId = "profile",
    params = mapOf("id" to "123"),
    type = Effect.Navigation.Type.PUSH
)
```

| Navigation Type | Behavior                        |
|-----------------|---------------------------------|
| `PUSH`          | Add screen to navigation stack  |
| `REPLACE`       | Replace current screen          |
| `POP`           | Remove current screen (go back) |

### Snackbar Effects

```kotlin
Effect.Snackbar(
    message = "Item saved successfully",
    isError = false,
    durationMs = 3000
)
```

### Dialog Effects

```kotlin
Effect.Dialog(
    title = "Confirm Action",
    message = "Are you sure?",
    actions = listOf(
        Effect.Dialog.DialogAction(
            label = "Yes",
            intent = Intent(id = "confirm_action", type = "DIALOG_ACTION", nodeKey = ""),
            isPrimary = true
        )
    )
)
```

## Custom Components

### 1. Define a Custom Payload

```kotlin
@Serializable
data class CustomVideoPayload(
    val url: String,
    val autoplay: Boolean = false
) : ComponentPayload
```

### 2. Create a Custom Renderer

```kotlin
object VideoRenderer : ComponentRenderer<CustomVideoPayload> {
    @Composable
    override fun render(
        node: BlueprintNode,
        payload: CustomVideoPayload,
        renderer: BlueprintRenderer
    ) {
        VideoPlayer(
            url = payload.url,
            autoplay = payload.autoplay,
            modifier = node.modifiers.toComposeModifier()
        )
    }
}
```

### 3. Register the Custom Renderer

```kotlin
val registry = createDefaultBlueprintRegistry().apply {
    register(CustomVideoPayload::class, VideoRenderer)
}
```

## Installation

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.numq:blueprint-runtime:1.0.0")
            implementation("io.github.numq:blueprint-dsl:1.0.0")
            implementation("io.github.numq:blueprint-renderer:1.0.0")
            implementation("io.github.numq:blueprint-renderer-compose:1.0.0")
        }
    }
}
```

## Example Project

The repository includes a complete example demonstrating:

- **Server**: Ktor-based server with three screens (Order List, Order Details, Tracking)
- **Client**: Desktop Compose Multiplatform application
- **Features**: Server-driven navigation, state management, dynamic content, and error handling

### Running the Example

```bash
cd example/server
./gradlew run

cd example/client
./gradlew run
```

## License

MIT License - see [LICENSE](LICENSE) file for details.

---

<p align="center">
  <a href="https://numq.github.io/support">
    <img src="https://api.qrserver.com/v1/create-qr-code/?size=112x112&data=https://numq.github.io/support&bgcolor=1a1b26&color=7aa2f7" 
         width="112" 
         height="112" 
         style="border-radius: 4px;" 
         alt="Support QR code">
  </a>
  <br>
  <a href="https://numq.github.io/support" style="text-decoration: none;">
    <code><font color="#bb9af7">Support Development: numq.github.io/support</font></code>
  </a>
</p>