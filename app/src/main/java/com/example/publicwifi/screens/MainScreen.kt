package com.example.publicwifi.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.publicwifi.components.MapComponent
import com.example.publicwifi.ui.theme.Primary
import com.example.publicwifi.ui.theme.Secondary
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var location by remember { mutableStateOf(false) }
    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val context = LocalContext.current
    getCurrentLocation(context) { result ->
        if (result != null) {
            location = true
            latLng = LatLng(result.latitude, result.longitude)
        } else {
            location = false
        }
    }

    if (location) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(brush = Brush.horizontalGradient(listOf(Secondary, Primary)))
                )
                {
                    Text(
                        text = "Public Wifi",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 24.sp,
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                WifiNearbySearchButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = { Log.d("MainScreen", "WifiNearbySearchButton Clicked") },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                WifiMapBox(modifier = Modifier, latLng = latLng)
            }

        }
    } else {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(brush = Brush.horizontalGradient(listOf(Secondary, Primary)))
                )
                {
                    Text(
                        text = "Public Wifi",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Text(
                    text = "지도를 불러오는 중",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WifiNearbySearchButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(10.dp)

    ) {
        Text(text = "내 주변 와이파이 검색", fontSize = fontSize, fontWeight = fontWeight)
    }
}

@Preview(showBackground = true)
@Composable
fun WifiNearbySearchButtonPreview() {
    WifiNearbySearchButton(modifier = Modifier)
}


@Composable
fun WifiMapBox(modifier: Modifier = Modifier, latLng: LatLng = LatLng(35.159545, 126.852601)) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        MapComponent(startLatLng = latLng, zoom = 15f)
    }
}

@Preview(showBackground = true)
@Composable
fun WifiMapBoxPreview() {
    WifiMapBox(modifier = Modifier)
}

@Composable
fun WifiListBox(modifier: Modifier = Modifier) {
}

// 위치정보 가져오는 함수
@Composable
fun getCurrentLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
    val currentActivity = LocalView.current.context as? Activity
    // 권한체크
    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context,
        locationPermission
    ) == PackageManager.PERMISSION_GRANTED

    if (!hasLocationPermission) {
        currentActivity?.let {
            ActivityCompat.requestPermissions(it, arrayOf(locationPermission), 0)
        }
    }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (hasLocationPermission) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                onLocationReceived(location)
            }
            .addOnFailureListener {
                onLocationReceived(null)
            }
    } else {
        onLocationReceived(null)
    }
}