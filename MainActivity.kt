package com.example.mc_assignment1
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.material3.LinearProgressIndicator
//import androidx.compose.ui.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.ui.fillMaxWidth

//import androidx.compose.ui.progress.ProgressBar
//import androidx.compose.ui.progress.ProgressBarDefaults

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mc_assignment1.ui.theme.MC_Assignment1Theme





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MC_Assignment1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApplication()
                }
            }
        }
    }
}



@Composable
fun MyApplication() {
    val stops = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "3", "14", "15")
    val distancesKm = listOf(20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300)
    val distanceUnitKm = remember { mutableStateOf(true) }
    val currentStopIndex = remember { mutableStateOf(0) }
    val totalDistanceKm = remember { mutableStateOf(distancesKm.sum()) }
    val totalDistanceMiles = remember { mutableStateOf(kmToMiles(totalDistanceKm.value)) }
    val distanceCoveredKm = remember { mutableStateOf(0) }
    val distanceCoveredMiles = remember { mutableStateOf(kmToMiles(distanceCoveredKm.value)) }
    val remainingDistanceKm = remember { mutableStateOf(totalDistanceKm.value - distanceCoveredKm.value) }
    val remainingDistanceMiles = remember { mutableStateOf(totalDistanceMiles.value - distanceCoveredMiles.value) }
    val stopsCovered = remember { mutableStateOf(0) }
    val progress = remember { mutableStateOf(0f) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LinearProgressIndicator(progress = progress.value / 100f,
                color = Color.Red, modifier = Modifier.fillMaxWidth())
            LazyColumn {
                items(stops) { stop ->
                    ListItem(stop, distancesKm[stops.indexOf(stop)], distanceUnitKm.value)
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            ) {
                Button(onClick = {
                    currentStopIndex.value++
                    distanceCoveredKm.value += distancesKm[currentStopIndex.value - 1]
                    distanceCoveredMiles.value = kmToMiles(distanceCoveredKm.value)
                    stopsCovered.value++
                    progress.value = (distanceCoveredKm.value.toDouble() / totalDistanceKm.value.toDouble() * 100).toFloat()
                    remainingDistanceKm.value = totalDistanceKm.value - distanceCoveredKm.value
                    remainingDistanceMiles.value = totalDistanceMiles.value - distanceCoveredMiles.value
                }) {
                    Text(text = "Next Stop")
                }
                Button(onClick = {
                    distanceUnitKm.value = !distanceUnitKm.value
                    totalDistanceMiles.value = kmToMiles(totalDistanceKm.value)
                    distanceCoveredMiles.value = kmToMiles(distanceCoveredKm.value)
                    remainingDistanceMiles.value = totalDistanceMiles.value - distanceCoveredMiles.value
                }) {
                    Text(text = "Toggle Distance Unit")
                }
            }
            Text(
                text = "Total Distance: ${
                    if (distanceUnitKm.value) "${totalDistanceKm.value} km"
                    else "${totalDistanceMiles.value} miles"
                }"
            )
            Text(
                text = "Distance Covered: ${
                    if (distanceUnitKm.value) "${distanceCoveredKm.value} km"
                    else "${distanceCoveredMiles.value} miles"
                }"
            )
            Text(
                text = "Remaining Distance: ${
                    if (distanceUnitKm.value) "${remainingDistanceKm.value} km"
                    else "${remainingDistanceMiles.value} miles"
                }"
            )
            Text(text = "Stops Covered: ${stopsCovered.value}")
        }
    }
}

@Composable
fun ListItem(stop: String, distance: Int, showInKm: Boolean) {
    val distanceText = if (showInKm) "$distance km" else "${kmToMiles(distance)} miles"
    Text(text = "Stop $stop: Distance: $distanceText")
}

fun kmToMiles(km: Int): Double {
    return km * 0.621371
}
@Preview(showBackground = true)
@Composable
fun PreviewMyApplication() {
    MyApplication()
}
