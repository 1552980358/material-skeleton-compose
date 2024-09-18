package me.ks.chan.material.skeleton.compose

data class SkeletonDurations internal constructor(
    internal val launch: Long,
    internal val leaving: SkeletonLeavingDurations<Int>,
    internal val awaiting: SkeletonAwaitingDurations<Long>,
) {

    override operator fun equals(other: Any?): Boolean {
        return this === other || (
            other is SkeletonDurations &&
                launch == other.launch &&
                leaving == other.leaving &&
                awaiting == other.awaiting
            )
    }

    override fun hashCode(): Int {
        var result = launch.hashCode()
        result = 31 * result + leaving.hashCode()
        result = 31 * result + awaiting.hashCode()
        return result
    }

}

internal data class SkeletonLeavingDurations<T>(
    override val placeholder: T, override val shimmer: T
): SkeletonState<T>, SkeletonDirectional<T>() {
    override val positive
        get() = placeholder
    override val negative
        get() = shimmer

    override fun equals(other: Any?): Boolean {
        return this === other || (
            other is SkeletonLeavingDurations<*> &&
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

internal data class SkeletonAwaitingDurations<T>(
    override val placeholder: T, override val shimmer: T
): SkeletonState<T>, SkeletonDirectional<T>() {

    override val positive
        get() = shimmer
    override val negative
        get() = placeholder

    override fun equals(other: Any?): Boolean {
        return this === other || (
            other is SkeletonAwaitingDurations<*> &&
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

fun SkeletonDurations(
    launch: Long,
    leaving: Pair<Int, Int>,
    awaiting: Pair<Long, Long>,
) = SkeletonDurations(
    launch = launch,
    leaving = SkeletonLeavingDurations(
        placeholder = leaving.first, shimmer = leaving.second
    ),
    awaiting = SkeletonAwaitingDurations(
        placeholder = awaiting.first, shimmer = awaiting.second
    ),
)
