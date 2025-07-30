package com.jayr.mapboxmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jayr.mapboxmap.ui.theme.MapboxMapTheme
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var apiKey = BuildConfig.MAPBOX_API_KEY
        MapboxOptions.accessToken = apiKey

        enableEdgeToEdge()
        setContent {
            MapboxMapTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapboxMap(
                        Modifier.fillMaxSize().padding(innerPadding),
                        mapViewportState = rememberMapViewportState {
                            setCameraOptions {
                                zoom(2.0)
                                center(Point.fromLngLat(-98.0, 39.5))
                                pitch(0.0)
                                bearing(0.0)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MapboxMapTheme {
        Greeting("Android")
    }
}