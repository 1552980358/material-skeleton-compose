package me.ks.chan.material.skeleton.compose

import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import kotlin.math.max
import kotlin.math.min

sealed interface SkeletonSize {

    context(MeasureScope) fun measure(placeable: Placeable): Pair<Int, Int>

    data object Auto : SkeletonSize {
        context(MeasureScope) override fun measure(placeable: Placeable): Pair<Int, Int> {
            return placeable.run { width to height }
        }
    }

    data class Min(
        internal val width: Dp = Dp.Unspecified,
        internal val height: Dp = Dp.Unspecified
    ): SkeletonSize {
        constructor(size: Dp): this(size, size)

        context(MeasureScope) override fun measure(placeable: Placeable): Pair<Int, Int> {
            val measuredWidth = when {
                width.isSpecified -> max(width.roundToPx(), placeable.width)
                else -> { placeable.width }
            }
            val measuredHeight = when {
                height.isSpecified -> max(height.roundToPx(), placeable.height)
                else -> { placeable.height }
            }
            return measuredWidth to measuredHeight
        }

        override fun equals(other: Any?): Boolean {
            return this === other || (
                other is Min &&
                    width.value == other.width.value &&
                    height.value == other.height.value
                )
        }

        override fun hashCode(): Int {
            return width.hashCode() * 31 + height.hashCode()
        }

    }

    data class Max(
        internal val width: Dp = Dp.Unspecified,
        internal val height: Dp = Dp.Unspecified
    ): SkeletonSize {
        constructor(size: Dp): this(size, size)

        context(MeasureScope) override fun measure(placeable: Placeable): Pair<Int, Int> {
            val measuredWidth = when {
                width.isSpecified -> min(width.roundToPx(), placeable.width)
                else -> { placeable.width }
            }
            val measuredHeight = when {
                height.isSpecified -> min(height.roundToPx(), placeable.height)
                else -> { placeable.height }
            }
            return measuredWidth to measuredHeight
        }

        override fun equals(other: Any?): Boolean {
            return this === other || (
                other is Max &&
                    width.value == other.width.value &&
                    height.value == other.height.value
                )
        }

        override fun hashCode(): Int {
            return width.hashCode() * 31 + height.hashCode()
        }

    }

    data class Exact(val width: Dp, val height: Dp): SkeletonSize {
        constructor(size: Dp): this(size, size)

        context(MeasureScope) override fun measure(placeable: Placeable): Pair<Int, Int> {
            return width.roundToPx() to height.roundToPx()
        }

        override fun equals(other: Any?): Boolean {
            return this === other || (
                other is Exact &&
                    width.value == other.width.value &&
                    height.value == other.height.value
                )
        }

        override fun hashCode(): Int {
            return width.hashCode() * 31 + height.hashCode()
        }

    }

}