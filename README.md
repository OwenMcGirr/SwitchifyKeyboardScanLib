# SwitchifyKeyboardScanLib

An Android library that enables keyboard developers to add Switchify scanning support to their keyboards. This library helps make keyboards accessible to users who rely on adaptive switches through the Switchify app.

## What is Switchify?

Switchify is an Android app that enables users with motor impairments to control their devices using adaptive switches. This library allows keyboard developers to make their keyboards compatible with Switchify's scanning functionality.

## Installation

Add JitPack repository to your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        // ... other repositories
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.enaboapps:SwitchifyKeyboardScanLib:1.0.2")
}
```

## Usage

The library provides two main components:

### 1. KeyboardSwitchifyNode Interface
Implement this interface in your keyboard key view class to make it scannable:

```kotlin
class YourKeyView : View(), KeyboardSwitchifyNode {
    override fun getPosition(): KeyboardNodePosition {
        return KeyboardNodePosition(
            x = x,
            y = y,
            width = width.toFloat(),
            height = height.toFloat()
        )
    }
}
```

### 2. KeyboardSwitchifyLink
Use this class to broadcast your keyboard's layout and visibility to Switchify:

```kotlin
class YourKeyboard : ViewGroup {
    private lateinit var switchifyLink: KeyboardSwitchifyLink
    
    private fun initSwitchify() {
        switchifyLink = KeyboardSwitchifyLink(context)
    }

    // When keyboard becomes visible:
    fun onKeyboardShow() {
        switchifyLink.showKeyboard(this)
    }

    // When keyboard is hidden:
    fun onKeyboardHide() {
        switchifyLink.hideKeyboard()
    }

    // When keyboard layout changes (e.g., switching between QWERTY and numbers):
    fun onLayoutChanged() {
        if (isVisible) {
            switchifyLink.captureAndBroadcastLayoutInfo(this)
        }
    }
}
```

The library will automatically:
1. Scan the ViewGroup hierarchy for views implementing `KeyboardSwitchifyNode`
2. Collect the positions and dimensions of all keyboard keys
3. Broadcast this information to the Switchify app using LocalBroadcastManager

### Broadcast Actions
The library uses the following broadcast actions:
- `com.enaboapps.ACTION_KEYBOARD_SHOW` - Sent when keyboard becomes visible, includes layout info
- `com.enaboapps.ACTION_KEYBOARD_HIDE` - Sent when keyboard is hidden
- `com.enaboapps.ACTION_KEYBOARD_LAYOUT_INFO` - Sent when keyboard layout changes

### API Methods
- `showKeyboard(keyboardView: ViewGroup)` - Call when keyboard becomes visible
- `hideKeyboard()` - Call when keyboard is hidden
- `captureAndBroadcastLayoutInfo(keyboardView: ViewGroup)` - Call when layout changes

## License

```
MIT License

Copyright (c) 2024 EnaboApps

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
``` 