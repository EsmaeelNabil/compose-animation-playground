package com.esmaeel.stmation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.esmaeel.stmation.ui.theme.StmationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StmationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var size by remember { mutableStateOf(IntSize.Zero) }
                    var showStickers by remember { mutableStateOf(false) }

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { size = it }
                    ) {


                        AnimatedShapesSystem(
                            onClick = { /* Do something on click */ },
                            emissionArea = Rect(
                                offset = Offset(
                                    size.width.toFloat() / 2,
                                    size.height.toFloat() / 2
                                ),
                                Size(size.width.toFloat(), size.height.toFloat())
                            ),
                            refreshRate = 40,
                            shapesColors = listOf(
                                Color.Magenta,
                                Color.Red,
                                Color.Blue,
                                Color.Green,
                                Color.Yellow,
                                Color.Cyan,
                                Color.Gray,
                                Color.Black,
                                Color.White,
                                Color(0xFF00FF00),
                                Color(0xFF00FFFF),
                                Color(0xFF0000FF),
                                Color(0xFFFF00FF),
                                Color(0xFFFF0000),
                                Color(0xFF000000),
                                Color(0xFFFFFFFF),
                            ),
                        )

                    }
                }
            }
        }
    }
}