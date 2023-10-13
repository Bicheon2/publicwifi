package com.example.publicwifi.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

const val GWANGJU_LAT = 35.159545
const val GWANGJU_LON = 126.852601
@Composable
fun MapComponent(
    location: LatLng = LatLng(GWANGJU_LAT, GWANGJU_LON)
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = location),
            title = "GWANGJU",
            snippet = "Marker in GWANGJU"
        )
    }
}