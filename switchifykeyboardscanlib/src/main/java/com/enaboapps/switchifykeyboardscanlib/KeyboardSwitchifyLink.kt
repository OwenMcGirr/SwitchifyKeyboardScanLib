package com.enaboapps.switchifykeyboardscanlib

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson

/**
 * Data class containing position and dimension information for a single keyboard key.
 *
 * @property x The X coordinate of the key in pixels
 * @property y The Y coordinate of the key in pixels
 * @property width The width of the key in pixels
 * @property height The height of the key in pixels
 */
data class KeyboardSwitchifyInfo(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

/**
 * Data class representing the complete layout information of a keyboard.
 *
 * @property keys List of [KeyboardSwitchifyInfo] objects, each representing a key in the keyboard layout
 */
data class KeyboardSwitchifyLayoutInfo(
    val keys: List<KeyboardSwitchifyInfo>
)

/**
 * Main class responsible for capturing and broadcasting keyboard layout information.
 * This class acts as a bridge between the keyboard view and any listeners interested in keyboard layout changes.
 *
 * It provides functionality to:
 * - Capture keyboard layout information
 * - Broadcast layout changes
 * - Send keyboard show/hide events
 *
 * @property context The Android context used for broadcasting events
 */
class KeyboardSwitchifyLink(private val context: Context) {

    companion object {
        /** Broadcast action for keyboard layout information updates */
        const val ACTION_KEYBOARD_LAYOUT_INFO = "com.enaboapps.ACTION_KEYBOARD_LAYOUT_INFO"
        /** Broadcast action for keyboard show events */
        const val ACTION_KEYBOARD_SHOW = "com.enaboapps.ACTION_KEYBOARD_SHOW"
        /** Broadcast action for keyboard hide events */
        const val ACTION_KEYBOARD_HIDE = "com.enaboapps.ACTION_KEYBOARD_HIDE"
        /** Extra key for keyboard layout information in broadcasts */
        const val EXTRA_KEYBOARD_LAYOUT_INFO = "keyboardLayoutInfo"
    }

    /**
     * Captures the current keyboard layout and broadcasts it to all registered listeners.
     *
     * @param keyboardView The ViewGroup containing the keyboard layout
     */
    fun captureAndBroadcastLayoutInfo(keyboardView: ViewGroup) {
        val layoutInfo = captureKeyboardLayoutInfo(keyboardView)
        broadcastLayoutInfo(layoutInfo)
    }

    /**
     * Captures the current keyboard layout and broadcasts a show event with the layout information.
     *
     * @param keyboardView The ViewGroup containing the keyboard layout
     */
    fun showKeyboard(keyboardView: ViewGroup) {
        val layoutInfo = captureKeyboardLayoutInfo(keyboardView)
        broadcastKeyboardShow(layoutInfo)
    }

    /**
     * Broadcasts a keyboard hide event to all registered listeners.
     */
    fun hideKeyboard() {
        broadcastKeyboardHide()
    }

    /**
     * Recursively captures layout information from the keyboard view.
     * Traverses through all child views and collects position information from [KeyboardSwitchifyNode] instances.
     *
     * @param keyboardView The ViewGroup to capture layout information from
     * @return [KeyboardSwitchifyLayoutInfo] containing the captured layout information
     */
    private fun captureKeyboardLayoutInfo(keyboardView: ViewGroup): KeyboardSwitchifyLayoutInfo {
        val keyboardSwitchifyInfos = mutableListOf<KeyboardSwitchifyInfo>()

        for (i in 0 until keyboardView.childCount) {
            val child = keyboardView.getChildAt(i)
            if (child !is KeyboardSwitchifyNode && child is ViewGroup) {
                // Recursive call for nested ViewGroup
                keyboardSwitchifyInfos.addAll(captureKeyboardLayoutInfo(child).keys)
            } else if (child is KeyboardSwitchifyNode) {
                // Add the node's position to the list
                val position = child.getPosition()
                keyboardSwitchifyInfos.add(
                    KeyboardSwitchifyInfo(
                        position.x.toInt(),
                        position.y.toInt(),
                        position.width.toInt(),
                        position.height.toInt()
                    )
                )
            }
        }

        return KeyboardSwitchifyLayoutInfo(keys = keyboardSwitchifyInfos)
    }

    /**
     * Broadcasts the captured keyboard layout information using LocalBroadcastManager.
     *
     * @param layoutInfo The keyboard layout information to broadcast
     */
    private fun broadcastLayoutInfo(layoutInfo: KeyboardSwitchifyLayoutInfo) {
        val jsonLayoutInfo = Gson().toJson(layoutInfo)
        val intent = Intent(ACTION_KEYBOARD_LAYOUT_INFO).apply {
            putExtra(EXTRA_KEYBOARD_LAYOUT_INFO, jsonLayoutInfo)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    /**
     * Broadcasts a keyboard show event with the current layout information.
     *
     * @param layoutInfo The keyboard layout information to include with the show event
     */
    private fun broadcastKeyboardShow(layoutInfo: KeyboardSwitchifyLayoutInfo) {
        val jsonLayoutInfo = Gson().toJson(layoutInfo)
        val intent = Intent(ACTION_KEYBOARD_SHOW).apply {
            putExtra(EXTRA_KEYBOARD_LAYOUT_INFO, jsonLayoutInfo)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    /**
     * Broadcasts a keyboard hide event.
     */
    private fun broadcastKeyboardHide() {
        val intent = Intent(ACTION_KEYBOARD_HIDE)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}