package me.ks.chan.material.skeleton.compose

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse

data class SkeletonColors(
    override val placeholder: Color,
    override val shimmer: Color
): SkeletonState<Color>, SkeletonDirectional<Color>() {

    override val positive: Color
        get() = shimmer
    override val negative: Color
        get() = placeholder

    override fun equals(other: Any?): Boolean {
        return this === other || (
            other is SkeletonColors &&
                placeholder == other.placeholder &&
                shimmer == other.shimmer &&
                positive == other.positive &&
                negative == other.negative
            )
    }

    override fun hashCode(): Int {
        var result = placeholder.hashCode()
        result = 31 * result + shimmer.hashCode()
        result = 31 * result + positive.hashCode()
        result = 31 * result + negative.hashCode()
        return result
    }

}

internal fun SkeletonColors(
    colorScheme: ColorScheme, colors: Pair<Color, Color>
) = SkeletonColors(
    placeholder = colors.first.takeOrElse(colorScheme::surface),
    shimmer = colors.second.takeOrElse(colorScheme::surfaceVariant)
)