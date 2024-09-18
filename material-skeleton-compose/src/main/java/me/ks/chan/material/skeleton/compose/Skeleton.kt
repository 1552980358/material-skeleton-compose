package me.ks.chan.material.skeleton.compose

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Skeleton(
    internal val colors: SkeletonColors,
    internal val size: SkeletonSize,
    internal val cornerRadius: CornerRadius,
    internal val durations: SkeletonDurations,
    internal val easing: SkeletonEasing,
) {

    override operator fun equals(other: Any?): Boolean {
        return this === other || (
            other is Skeleton &&
                colors == other.colors &&
                size == other.size &&
                cornerRadius == other.cornerRadius &&
                durations == other.durations &&
                easing == other.easing
            )
    }

    override fun hashCode(): Int {
        var result = colors.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + cornerRadius.hashCode()
        result = 31 * result + durations.hashCode()
        result = 31 * result + easing.hashCode()
        return result
    }

}

@Composable
fun rememberSkeleton(factory: SkeletonFactory.() -> Unit = {}): Skeleton {
    val colorScheme = MaterialTheme.colorScheme
    val density = LocalDensity.current
    return remember {
        SkeletonFactory(factory).build(colorScheme, density)
    }
}

data class SkeletonFactory(
    private var colors: Pair<Color, Color> =
        Color.Unspecified to Color.Unspecified,
    var size: SkeletonSize = SkeletonSize.Auto,
    var cornerRadius: Dp = 12.dp,
    var launchDelay: Long = 0L,
    private var leavingDurations: Pair<Int, Int> = 400 to 400,
    private var awaitingDurations: Pair<Long, Long> = 0L to 0L,
    private var easing: Pair<Easing, Easing> = FastOutSlowInEasing to FastOutSlowInEasing,
) {

    constructor(factory: SkeletonFactory.() -> Unit): this() {
        factory()
    }

    /***********************************************************************/
    /***********************************************************************/

    operator fun invoke(factory: SkeletonFactory.() -> Unit) {
        factory()
    }

    fun colors(
        placeholder: Color = Color.Unspecified,
        shimmer: Color = Color.Unspecified,
    ) {
        colors = placeholder to shimmer
    }

    fun leaving(placeholder: Int = 400, shimmer: Int = 400) {
        leavingDurations = placeholder to shimmer
    }

    fun awaiting(placeholder: Long = 0, shimmer: Long = 0) {
        awaitingDurations = placeholder to shimmer
    }

    fun easing(placeholder: Easing = FastOutSlowInEasing, shimmer: Easing = FastOutSlowInEasing) {
        easing = placeholder to shimmer
    }

    internal fun build(
        colorScheme: ColorScheme, density: Density
    ) = Skeleton(
        colors = SkeletonColors(colorScheme, colors),
        size = size,
        durations = SkeletonDurations(
            launchDelay, leavingDurations, awaitingDurations
        ),
        cornerRadius = with(density) { CornerRadius(cornerRadius.toPx()) },
        easing = SkeletonEasing(easing),
    )

}