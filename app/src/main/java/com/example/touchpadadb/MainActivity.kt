package com.example.touchpadadb

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.touchpadadb.ui.theme.TouchpadADBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TouchpadADBTheme {
                TouchpadSurface()
            }
        }
    }
}

@Composable
fun TouchpadSurface() {
    var lastEvent: Point?

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(4, 17, 33),
    ) {
        Column(
            modifier = Modifier
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        do {
                            awaitFirstDown()
                            lastEvent = null

                            do {
                                val event: PointerEvent = awaitPointerEvent()
                                val point = Point(
                                    event.changes.first().position.x.toInt(),
                                    event.changes.first().position.y.toInt())

                                if (lastEvent == null) {
                                    lastEvent = point
                                }

                                val dx = point.x - lastEvent!!.x
                                val dy = point.y - lastEvent!!.y
                                Log.i("touch_event", "$dx $dy")

                                lastEvent = point
                            } while (event.changes.any { it.pressed })
                        } while (true)
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Touch Pad ADB",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Preview
@Composable
fun PreviewTouchpad() {
    TouchpadSurface()
}

