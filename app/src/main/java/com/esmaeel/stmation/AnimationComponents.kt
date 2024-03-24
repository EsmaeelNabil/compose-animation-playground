package com.esmaeel.stmation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedShapesSystem(
    onClick: () -> Unit,
    emissionArea: Rect,
    shapesColors: List<Color> = listOf(Color.Magenta),
    sizeRange: IntRange = 5..20,
    refreshRate: Long = 100
) {
    var emittionSeed by remember { mutableIntStateOf(0) }
    val particles = remember { mutableStateListOf<Particle>() }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                emittionSeed = Random.Default.nextInt()
                onClick()
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            particles.forEach { particle ->
                drawCircle(
                    color = particle.color,
                    radius = particle.size,
                    center = particle.position
                )
            }
        }

        LaunchedEffect(emittionSeed) {
            repeat(1000) {
                particles.add(
                    createHeartParticle(
                        emissionArea = emissionArea,
                        shapesColors = shapesColors,
                        sizeRange = sizeRange
                    )
                )
            }
            delay(1000)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(refreshRate)
            particles.removeAll { it.isDead() }
            particles.forEach { it.update() }
        }
    }
}

data class Particle(
    var position: Offset,
    val velocity: Offset,
    val color: Color,
    val size: Float,
    var alpha: Float = 1f,
    val lifeTime: Long
) {
    fun update() {
        position += velocity
        alpha -= 0.02f
    }

    fun isDead() = alpha <= 0f || System.currentTimeMillis() >= lifeTime
}


private fun createHeartParticle(
    emissionArea: Rect,
    shapesColors: List<Color>,
    sizeRange: IntRange = 5..20
): Particle {
    val random = Random.Default
    val position = emissionArea.randomOffset()
    val velocity = Offset(x = random.nextFloat() * 10f - 1f, y = -random.nextFloat() * 10f)
    val size = random.nextInt(sizeRange.first, sizeRange.last).toFloat()
    return Particle(
        position = position,
        velocity = velocity,
        color = shapesColors.random(),
        size = size,
        lifeTime = System.currentTimeMillis() + random.nextInt(50, 10000)
    )
}

private fun Rect.randomOffset(): Offset {
    val random = Random.Default
    return Offset(
        x = random.nextFloat() * width,
        y = random.nextFloat() * height
    )
}