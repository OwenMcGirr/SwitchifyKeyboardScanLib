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
    implementation("com.github.enaboapps:SwitchifyKeyboardScanLib:1.0.0")
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
Use this class to capture and broadcast your keyboard's layout to Switchify:

```kotlin
class YourKeyboard : ViewGroup {
    private lateinit var switchifyLink: KeyboardSwitchifyLink
    
    private fun initSwitchify() {
        switchifyLink = KeyboardSwitchifyLink(context)
        // Call this whenever your keyboard layout changes
        switchifyLink.captureAndBroadcastLayoutInfo(this)
    }
}
```

The library will automatically:
1. Collect the positions and dimensions of all keyboard keys
2. Broadcast this information to the Switchify app
3. Enable Switchify to provide scanning functionality across your keyboard

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