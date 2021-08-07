package com.nishant.compose.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nishant.compose.ui.activities.viewmodels.MainViewModel
import com.nishant.compose.ui.composables.Clocks
import com.nishant.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme(false) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Clocks()
                }
            }
        }
    }
}