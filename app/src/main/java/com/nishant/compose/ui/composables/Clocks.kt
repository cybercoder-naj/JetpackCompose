package com.nishant.compose.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nishant.compose.ui.activities.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun Clocks(mainViewModel: MainViewModel = viewModel()) {
    val delhi: IntArray by mainViewModel.timeDelhi.observeAsState(intArrayOf(0, 0, 0))
    val newYork: IntArray by mainViewModel.timeNewYork.observeAsState(intArrayOf(0, 0, 0))
    val london: IntArray by mainViewModel.timeLondon.observeAsState(intArrayOf(0, 0, 0))
    val tokyo: IntArray by mainViewModel.timeTokyo.observeAsState(intArrayOf(0, 0, 0))

    val clocks = listOf(
        "New Delhi, India" to delhi,
        "New York, USA" to newYork,
        "London, England" to london,
        "Berlin, Germany" to tokyo
    )

    LaunchedEffect(key1 = delhi) {
        delay(1000)
        mainViewModel.tickTime()
    }

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(clocks) { clock ->
            ClockCard(name = clock.first, time = clock.second)
        }
    }
}

@Composable
fun ClockCard(name: String, time: IntArray) {
    Column(
        modifier = Modifier.border(.5.dp, Color.Black)
    ) {
        Text(
            name,
            modifier = Modifier
                .padding(vertical = 32.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Clock(time = time)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun Clock(time: IntArray) {
    if (time.size != 3)
        return

    val (hr, min, sec) = time

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.size(150.dp, 150.dp),
            shape = CircleShape,
            color = Color.White,
            elevation = 5.dp
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val width = size.width
                    val height = size.height

                    for (i in 0..59) {
                        if (i % 5 == 0)
                            continue

                        rotate(i * 6f) {
                            drawLine(
                                color = Color.Black,
                                start = Offset(width / 2, 0f),
                                end = Offset(width / 2, height),
                                strokeWidth = (.5).dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }

                    drawCircle(
                        color = Color.White,
                        radius = size.minDimension / 2.25f
                    )

                    for (i in 0..5) {
                        rotate(i * 30f) {
                            drawLine(
                                color = Color.Black,
                                start = Offset(width / 2, 0f),
                                end = Offset(width / 2, height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                    }

                    drawCircle(
                        color = Color.White,
                        radius = size.minDimension / 2.5f
                    )
                }

                Canvas(
                    modifier = Modifier.fillMaxSize(.5f)
                ) {
                    val width = size.width
                    val height = size.height

                    rotate(30f * hr + .5f * min + .008f * sec) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(width / 2, height / 2),
                            end = Offset(width / 2, 0f),
                            strokeWidth = 2.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }

                Canvas(
                    modifier = Modifier.fillMaxSize(.8f)
                ) {
                    val width = size.width
                    val height = size.height

                    rotate(6f * min + .1f * sec) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(width / 2, height / 2),
                            end = Offset(width / 2, 0f),
                            strokeWidth = 1.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    rotate(6f * sec) {
                        drawLine(
                            color = Color.Red,
                            start = Offset(width / 2, height / 2),
                            end = Offset(width / 2, 0f),
                            strokeWidth = 1.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    drawPoints(
                        points = listOf(Offset(width / 2, height / 2)),
                        pointMode = PointMode.Points,
                        color = Color.Blue,
                        strokeWidth = 8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClock() {
    Clock(intArrayOf(3, 25, 51))
}