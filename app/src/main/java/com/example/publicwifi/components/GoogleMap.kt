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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerInfoWindowContent
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

@Composable
fun MapComponent(
    startLatLng: LatLng = LatLng(GWANGJU_LAT, GWANGJU_LON),
    latLngList: Array<LatLng> = ARRAY_LATLNG,
    zoom: Float = 10f,
    context: Context = LocalContext.current,
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
        if(latLngList.isNotEmpty()) {
            latLngList.forEachIndexed { index, latLng ->

                MarkerInfoWindow(
                    state = MarkerState(position = latLng),
                    icon = bitmapDescriptorFromVector(
                        context = context,
                        vectorResourceId = com.example.publicwifi.R.drawable.profile,
                        width = 100,
                        height = 100,
                    ),
                ) { marker ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(35.dp)
                        )
                ){
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

//                        Image(
//                            painter = painterResource(id = R.drawable.map),
//                            contentDescription = null,
//                            contentScale = ContentScale.Fit,
//                            modifier = Modifier
//                                .height(80.dp)
//                                .fillMaxWidth(),
//
//                            )
                        //.........................Spacer
                        Spacer(modifier = Modifier.height(24.dp))
                        //.........................Text: title
                        Text(
                            text = "Marker Title",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        //.........................Text : description
                        Text(
                            text = "Customizing a marker's info window",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        //.........................Spacer
                        Spacer(modifier = Modifier.height(24.dp))

                    }
                }

                }
            }
//            MarkerInfoWindowContent(
//                state = MarkerState(position = latLngList[0]),
//            )
        }
    }
}


//fun bitmapDescriptorFromVector(
//    context: Context,
//    @DrawableRes vectorResourceId: Int
//): BitmapDescriptor {
//    val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResourceId)
//    vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
//    val bitmap: Bitmap = Bitmap.createBitmap(
//        vectorDrawable?.intrinsicWidth ?: 0,
//        vectorDrawable?.intrinsicHeight ?: 0,
//        Bitmap.Config.ARGB_8888
//    )
//    val canvas = Canvas(bitmap)
//    vectorDrawable?.draw(canvas)
//    return BitmapDescriptorFactory.fromBitmap(bitmap)
//}

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