package com.enaboapps.switchifykeyboardscanlib

import android.graphics.PointF

/**
 * Interface for a node in the keyboard switchify graph.
 * A node is a point in the keyboard layout where a switchify action can be performed.
 * You should implement this interface in your keyboard key class to enable switchify functionality.
 */
interface KeyboardSwitchifyNode {
    fun getPosition(): KeyboardNodePosition
}

data class KeyboardNodePosition(val x: Float, val y: Float, val width: Float, val height: Float)