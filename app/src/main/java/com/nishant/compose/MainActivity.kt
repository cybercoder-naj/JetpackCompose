package com.nishant.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.delay
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                val end = 100f

                var currentValue by remember { mutableStateOf(0f) }
                val current by animateFloatAsState(
                    targetValue = currentValue,
                    tween(durationMillis = 2500)
                )

                LaunchedEffect(key1 = currentValue) {
                    delay(100)
                    currentValue = end
                }

                Box(
                    modifier = Modifier.fillMaxSize().background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBar(current = current, total = end)
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar(current: Float, total: Float) {
    val progress = current / total

    Box(
        modifier = Modifier
            .width(128.dp)
            .height(128.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                size = Size(size.width, size.height),
                style = Stroke(4.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${round(progress * 1000f) / 10f}%",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
    }
}