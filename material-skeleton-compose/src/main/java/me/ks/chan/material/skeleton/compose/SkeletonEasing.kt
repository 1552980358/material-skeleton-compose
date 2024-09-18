package me.ks.chan.material.skeleton.compose

import androidx.compose.animation.core.Easing

data class SkeletonEasing(
    override val placeholder: Easing, override val shimmer: Easing
): SkeletonDirectional<Easing>(), SkeletonState<Easing> {

    override val positive: Easing
        get() = placeholder
    override val negative: Easing
        get() = shimmer

    override fun equals(other: Any?): Boolean {
        return this === other || (
            other is SkeletonEasing &&
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

fun SkeletonEasing(easing: Pair<Easing, Easing>) = SkeletonEasing(
    placeholder = easing.first, shimmer = easing.second
)