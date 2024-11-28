package com.enaboapps.switchifykeyboardscanlib

/**
 * Interface for a node in the keyboard Switchify system.
 * Represents a key or interactive element in a keyboard layout that can participate in Switchify actions.
 * 
 * Implement this interface in your keyboard key class to enable Switchify functionality.
 * Each node should provide its position and dimensions through the [getPosition] method.
 */
interface KeyboardSwitchifyNode {
    /**
     * Retrieves the position and dimensions of this keyboard node.
     *
     * @return [KeyboardNodePosition] containing the x, y coordinates and dimensions of the node
     */
    fun getPosition(): KeyboardNodePosition
}

/**
 * Data class representing the position and dimensions of a keyboard node.
 *
 * @property x The X coordinate of the node in pixels
 * @property y The Y coordinate of the node in pixels
 * @property width The width of the node in pixels
 * @property height The height of the node in pixels
 */
data class KeyboardNodePosition(val x: Float, val y: Float, val width: Float, val height: Float)