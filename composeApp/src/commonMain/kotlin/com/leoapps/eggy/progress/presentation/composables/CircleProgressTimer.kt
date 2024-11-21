package com.leoapps.progress.presentation.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.toIntSize
import com.leoapps.eggy.base.ui.theme.GrayLight
import com.leoapps.eggy.base.ui.theme.Primary
import com.leoapps.eggy.base.ui.theme.PrimaryAlmostWhite
import com.leoapps.eggy.base.ui.theme.White
import com.leoapps.eggy.common.utils.EMPTY_CALCULATED_TIME
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.timer_background
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val SMALL_TO_BIG_RADIUS_RATIO = 0.75f

class TimerState(initial: Float) {

    var progressText: String by mutableStateOf(EMPTY_CALCULATED_TIME)
    var progress: Float by mutableFloatStateOf(initial)
        private set

    suspend fun setProgress(progress: Float) {
        animate(
            initialValue = this.progress,
            targetValue = progress,
            initialVelocity = 0f,
            animationSpec = spring(
                stiffness = Spring.StiffnessVeryLow,
            ),
        ) { animatedValue, velocity ->
            this.progress = animatedValue
        }
    }

    companion object {
        val Saver: Saver<TimerState, *> = Saver(
            save = { it.progress },
            restore = { TimerState(it) }
        )
    }
}

@Composable
fun rememberTimerState(initial: Float = 0f): TimerState {
    return rememberSaveable(saver = TimerState.Saver) {
        TimerState(initial = initial)
    }
}

@Composable
fun CircleTimer(
    state: TimerState = rememberTimerState(),
    modifier: Modifier = Modifier,
) {
    val bitmapBackground = imageResource(Res.drawable.timer_background)
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.headlineLarge

    Spacer(
        modifier = modifier
            .drawWithCache {
                val strokeWidth = size.width / 25
                val radiusUnrounded = (size.width * SMALL_TO_BIG_RADIUS_RATIO - strokeWidth) / 2
                val radius = radiusUnrounded.toInt().toFloat()
                val circleSize = Size(radius * 2, radius * 2)

                val topLeftOffset = Offset(
                    x = size.center.x - radius,
                    y = size.center.y - radius
                )

                val indicatorRadiusBig = strokeWidth * 1.5f
                val indicatorRadiusSmall = indicatorRadiusBig * 0.75f
                val angle = 2f * PI * state.progress
                val x = sin(angle) * radius
                val y = cos(angle) * radius
                val newCenter = Offset(
                    x = size.center.x + x.toFloat(),
                    y = size.center.y - y.toFloat(),
                )

                val textMeasureResult = textMeasurer.measure(
                    text = state.progressText,
                    style = textStyle,
                )
                val textOffset = topLeftOffset + Offset(
                    x = radius - textMeasureResult.size.width / 2,
                    y = (radius - textMeasureResult.size.height) / 2,
                )

                onDrawWithContent {
                    // Draw a bitmap background
                    drawImage(
                        image = bitmapBackground,
                        dstSize = size.toIntSize(),
                    )

                    // Draw the background circle
                    drawCircle(
                        color = GrayLight,
                        radius = radius,
                        center = size.center,
                        style = Stroke(
                            width = strokeWidth
                        )
                    )

                    // Draw progress arc
                    rotate(-90f, size.center) {
                        drawArc(
                            brush = Brush.sweepGradient(
                                0f to PrimaryAlmostWhite,
                                (state.progress - 0.2f) to Primary,
                                center = size.center
                            ),
                            topLeft = topLeftOffset,
                            size = circleSize,
                            startAngle = 0f,
                            sweepAngle = 360f * state.progress,
                            useCenter = false,
                            style = Stroke(
                                width = strokeWidth
                            )
                        )
                    }

                    // Draw the decoration circle at the end of the progress
                    drawCircle(
                        color = Primary,
                        radius = indicatorRadiusBig,
                        center = newCenter,
                        style = Fill
                    )
                    drawCircle(
                        color = White,
                        radius = indicatorRadiusSmall,
                        center = newCenter,
                        style = Fill
                    )

                    // Draw timer text
                    drawText(
                        textLayoutResult = textMeasureResult,
                        topLeft = textOffset,
                    )
                }
            }
    )
}