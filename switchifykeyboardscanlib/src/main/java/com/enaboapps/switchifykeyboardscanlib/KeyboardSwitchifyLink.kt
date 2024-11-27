package com.enaboapps.switchifykeyboardscanlib

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson

data class KeyboardSwitchifyInfo(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

data class KeyboardSwitchifyLayoutInfo(
    val keys: List<KeyboardSwitchifyInfo>
)

class KeyboardSwitchifyLink(private val context: Context) {

    companion object {
        const val ACTION_KEYBOARD_LAYOUT_INFO = "com.enaboapps.ACTION_KEYBOARD_LAYOUT_INFO"
        const val ACTION_KEYBOARD_SHOW = "com.enaboapps.ACTION_KEYBOARD_SHOW"
        const val ACTION_KEYBOARD_HIDE = "com.enaboapps.ACTION_KEYBOARD_HIDE"
        const val EXTRA_KEYBOARD_LAYOUT_INFO = "keyboardLayoutInfo"
    }

    fun captureAndBroadcastLayoutInfo(keyboardView: ViewGroup) {
        val layoutInfo = captureKeyboardLayoutInfo(keyboardView)
        broadcastLayoutInfo(layoutInfo)
    }

    fun showKeyboard(keyboardView: ViewGroup) {
        val layoutInfo = captureKeyboardLayoutInfo(keyboardView)
        broadcastKeyboardShow(layoutInfo)
    }

    fun hideKeyboard() {
        broadcastKeyboardHide()
    }

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

    private fun broadcastLayoutInfo(layoutInfo: KeyboardSwitchifyLayoutInfo) {
        val jsonLayoutInfo = Gson().toJson(layoutInfo)
        val intent = Intent(ACTION_KEYBOARD_LAYOUT_INFO).apply {
            putExtra(EXTRA_KEYBOARD_LAYOUT_INFO, jsonLayoutInfo)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    private fun broadcastKeyboardShow(layoutInfo: KeyboardSwitchifyLayoutInfo) {
        val jsonLayoutInfo = Gson().toJson(layoutInfo)
        val intent = Intent(ACTION_KEYBOARD_SHOW).apply {
            putExtra(EXTRA_KEYBOARD_LAYOUT_INFO, jsonLayoutInfo)
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    private fun broadcastKeyboardHide() {
        val intent = Intent(ACTION_KEYBOARD_HIDE)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}