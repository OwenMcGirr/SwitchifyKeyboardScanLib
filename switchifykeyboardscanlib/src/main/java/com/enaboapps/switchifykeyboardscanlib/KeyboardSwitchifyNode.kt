package com.enaboapps.switchifykeyboardscanlib

/**
 * Interface for a node in the keyboard Switchify system.
 * Represents a key or interactive element in a keyboard layout that can participate in Switchify actions.
 * 
 * Implement this interface in your keyboard key class to enable Switchify functionality.
 * Each node should provide its own implementation of [getSwitchifyKeyboardNodeInfo] to retrieve information about its position, dimensions, and other properties.
 */
interface KeyboardSwitchifyNode {
    /**
     * Retrieves the information about the node's position, dimensions, and other properties.
     *
     * @return [KeyboardNodeInfo] containing the node's position, dimensions, and other properties
     */
    fun getSwitchifyKeyboardNodeInfo(): KeyboardNodeInfo
}

/**
 * Data class representing the position, dimensions, and other properties of a keyboard node.
 *
 * @property x The X coordinate of the node in pixels
 * @property y The Y coordinate of the node in pixels
 * @property width The width of the node in pixels
 * @property height The height of the node in pixels
 * @property contentDescription The content description of the node
 */
data class KeyboardNodeInfo(val x: Float, val y: Float, val width: Float, val height: Float, val contentDescription: String)