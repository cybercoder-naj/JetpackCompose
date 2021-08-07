package com.nishant.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nishant.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mProjects = listOf(
            ProjectModel("SmartHomeApp", "July 12, 2021", 52f),
            ProjectModel("Jetpack Compose", "August 24, 2021", 20f),
            ProjectModel("Learn Vue js", "July 20, 2021", 90f)
        )

        setContent {
            ComposeTheme {
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Title(name = "Nishant A. Jalan")

                        Text(text = "My Projects")

                        Projects(mProjects)
                    }
                }
            }
        }
    }
}

@Composable
fun Title(name: String, modifier: Modifier = Modifier) {
    Box {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Column {
                Text(text = "Good evening,", color = Color.DarkGray)
                Text(text = name, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Image(
                painter = painterResource(R.drawable.nishant),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
fun Projects(projects: List<ProjectModel>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(start = 8.dp)
    ) {
        items(projects) { project ->
            ProjectCard(project = project)
        }
    }
}

@Composable
fun ProjectCard(project: ProjectModel) {
    var animationTargetState by remember { mutableStateOf(0f) }

    val percent by animateFloatAsState(
        targetValue = animationTargetState,
        animationSpec = tween(1000)
    )

    LaunchedEffect(key1 = animationTargetState) {
        delay(100)
        animationTargetState = project.percentDone
    }

    Card(
        modifier = Modifier
            .shadow(8.dp)
            .width(280.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(text = project.title, fontWeight = FontWeight.Bold)
            Text(text = project.date, color = Color.DarkGray, fontSize = 12.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ProgressPercentageBar(percent = percent, when (project.percentDone) {
                    in (0f)..(40f) -> Color.Red
                    in (40f)..(70f) -> Color(0xFFFFA500)
                    in (70f)..(100f) -> Color.Green
                    else -> TODO("Percent must be between 0 and 100.")
                })

                Text(
                    text = buildAnnotatedString {
                        append(project.percentDone.toString())
                        append('%')
                    },
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTitle() {
    ProjectCard(project = ProjectModel("SmartHomeApp", "July 12, 2021", 52f))
}

@Composable
fun ProgressPercentageBar(
    percent: Float,
    foregroundColor: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth(.75f)
            .padding(vertical = 16.dp)
            .height(8.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawLine(
            color = Color(0xFFEEEEEE),
            start = Offset(0f, 0f),
            end = Offset(canvasWidth, 0f),
            strokeWidth = canvasHeight,
            cap = StrokeCap.Round
        )

        drawLine(
            color = foregroundColor,
            start = Offset(0f, 0f),
            end = Offset(canvasWidth * percent / 100f, 0f),
            strokeWidth = canvasHeight,
            cap = StrokeCap.Round
        )
    }
}

data class ProjectModel(
    val title: String,
    val date: String,
    val percentDone: Float
)