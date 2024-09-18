package me.ks.chan.material.skeleton.compose

internal interface SkeletonState<T> {
    val placeholder: T
    val shimmer: T
}

abstract class SkeletonDirectional<T> {

    operator fun invoke(direction: Boolean): T = when {
        direction -> { positive }
        else -> { negative }
    }

    protected abstract val positive: T
    protected abstract val negative: T

}