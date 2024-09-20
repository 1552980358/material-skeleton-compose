package me.ks.chan.material.skeleton.compose

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.node.invalidateMeasurement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun Modifier.skeleton(
    skeleton: Skeleton,
    enabled: Boolean = true,
) = this then SkeletonNodeElement(
    enabled = enabled, skeleton = skeleton,
)

fun Modifier.skeleton(
    enabled: Boolean = true,
    factory: SkeletonFactory.() -> Unit = {},
) = composed {
    skeleton(skeleton = rememberSkeleton(factory), enabled = enabled)
}

private class SkeletonNodeElement(
    private val enabled: Boolean,
    private val skeleton: Skeleton,
): ModifierNodeElement<SkeletonNode>() {

    override fun create() = SkeletonNode(enabled, skeleton)

    override fun update(node: SkeletonNode) {
        node.enabled = enabled
    }

    override fun InspectorInfo.inspectableProperties() {
        name = SkeletonNodeElement::class.simpleName
        properties["enabled"] = enabled
        properties["skeleton"] = skeleton
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (
            other is SkeletonNodeElement &&
                enabled == other.enabled &&
                skeleton == other.skeleton
            )
    }

    override fun hashCode(): Int {
        return enabled.hashCode() * 31 + skeleton.hashCode()
    }

}

private class SkeletonNode(
    enabled: Boolean,
    private val skeleton: Skeleton,
): Modifier.Node(), LayoutModifierNode, DrawModifierNode {

    var enabled: Boolean = enabled
        set(value) {
            if (field != value) {
                field = value
                if (value && isAttached) {
                    // Reuse onAttach() to restart the flow
                    onAttach()
                }
            }
        }

    private sealed interface State {
        data object Initializing: State
        data object Operating: State
        data object Leaving: State
        data object Disabled: State
    }

    private var state: State = State.Initializing
    private val animatable = Animatable(skeleton.colors.placeholder)

    private val flow = flow {
        state = State.Initializing
        if (animatable.value != skeleton.colors.placeholder) {
            animatable.snapTo(skeleton.colors.shimmer)
            invalidateDraw()
        }

        state = State.Operating
        /**
         * true: towards shimmer
         * false: towards placeholder
         **/
        var direction = true

        // Launch delay
        delay(skeleton.durations.launch)

        while (this@SkeletonNode.enabled) {
            animatable.animateTo(
                skeleton.colors(direction),
                animationSpec = tween(
                    durationMillis = skeleton.durations.leaving(direction),
                    easing = skeleton.easing(direction)
                ),
                block = { invalidateDraw() }
            )
            if (this@SkeletonNode.enabled) {
                delay(skeleton.durations.awaiting(direction))
            }
            direction = direction.not()
        }

        state = State.Leaving
        /**
         * Disabled after direction == true (direction is changed before checking `enabled`)
         **/
        if (direction) {
            animatable.animateTo(
                skeleton.colors.shimmer,
                animationSpec = tween(
                    durationMillis = skeleton.durations.leaving.placeholder,
                    easing = skeleton.easing(true)
                ),
                block = { invalidateDraw() }
            )
        }

        state = State.Disabled
        // Leave flow
        emit(Unit)
    }

    override fun onAttach() {
        if (this.enabled) {
            coroutineScope.launch {
                flow.collect {
                    invalidateDraw()
                }
                invalidateMeasurement()
            }
        }
    }

    private var drawSize = Size.Unspecified

    private val drawSkeleton: Boolean
        get() = enabled || state != State.Disabled

    override fun MeasureScope.measure(
        measurable: Measurable, constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)

        val (width, height) = (if (drawSkeleton) skeleton.size else SkeletonSize.Auto)
            .measure(placeable)

        if (drawSkeleton) {
            drawSize = Size(width.toFloat(), height.toFloat())
        }

        return layout(width, height) {
            placeable.place(0, 0)
        }
    }

    override fun ContentDrawScope.draw() {
        when {
            drawSkeleton -> {
                drawRoundRect(
                    color = animatable.value,
                    size = drawSize,
                    cornerRadius = skeleton.cornerRadius
                )
            }
            else -> { drawContent() }
        }
    }

}