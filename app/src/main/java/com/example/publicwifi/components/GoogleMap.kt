package com.example.publicwifi.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifi4Bar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.publicwifi.api.dto.WifiDto
import com.example.publicwifi.screens.WifiMapBox
import com.example.publicwifi.screens.WifiNearbySearchButton
import com.example.publicwifi.ui.theme.Primary
import com.example.publicwifi.ui.theme.Secondary
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

const val GWANGJU_LAT = 35.159545
const val GWANGJU_LON = 126.852601

val ARRAY_LATLNG: Array<LatLng> = arrayOf(
    LatLng(35.1235438, 126.8628229),
    LatLng(35.1226706, 126.8641426),
    LatLng(35.1224205, 126.8652342),
    LatLng(35.1218126, 126.8628161),
    LatLng(35.1222602, 126.8618344),

    )
val ARRAY_INFO: Array<String> = arrayOf(
    "테스트1",
    "테스트2",
    "테스트3",
    "테스트4",
    "테스트5",

    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapComponent(
    startLatLng: LatLng = LatLng(GWANGJU_LAT, GWANGJU_LON),
    latLngList: Array<LatLng> = ARRAY_LATLNG,
    zoom: Float = 10f,
    context: Context = LocalContext.current,
    wifiList: List<WifiDto?> = listOf(),
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLatLng, zoom)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = startLatLng),
            title = "현재 위치",
        )
        if (wifiList.isNotEmpty()) {
            wifiList.forEachIndexed { index, wifiData ->

                MarkerInfoWindow(
                    state = MarkerState(position = LatLng(wifiData?.lat!!.toDouble(), wifiData?.lng!!.toDouble())),
                    icon = bitmapDescriptorFromVector(
                        context = context,
                        vectorResourceId = com.example.publicwifi.R.drawable.wifi_svgrepo_com,
                        width = 100,
                        height = 100,
                    ),
                ) { marker ->
                    Scaffold(
                        topBar = {
                            Box(
                                modifier = Modifier
                                    .height(25.dp)
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                Secondary,
                                                Primary
                                            )
                                        )
                                    )
                            )
                            {
                                Text(
                                    text = "Public Wifi",
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 18.sp,
                                )
                            }
                        },
                        modifier = Modifier.padding(horizontal = 60.dp).height(100.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(it)
                        ) {
                            Text(
                                text = "${wifiData?.ssid}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}


fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorResourceId: Int,
    width: Int, // 원하는 너비
    height: Int // 원하는 높이
): BitmapDescriptor {
    val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResourceId)
    vectorDrawable?.setBounds(0, 0, width, height)
    val bitmap: Bitmap = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}