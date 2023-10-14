package com.example.publicwifi.screens

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.publicwifi.api.dto.WifiDto
import com.example.publicwifi.components.MapComponent
import com.example.publicwifi.ui.theme.Primary
import com.example.publicwifi.ui.theme.Secondary
import com.example.publicwifi.viewmodel.LifeCycleViewModel
import com.example.publicwifi.viewmodel.WifiViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var wifiViewModel: WifiViewModel = hiltViewModel()
    val wifiList by wifiViewModel.wifiList.collectAsState()
    LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.IO){ wifiViewModel.getWifiList()}
        Log.d("MainScreenn", "$wifiList")
    }
//    var wifiList: List<WifiDto?> = listOf()
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
                WifiListBox(context = context, wifiList = wifiList)
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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun WifiListBox(
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    wifiList: List<WifiDto?>
) {
    Log.d("WifiListBox", "wifiList : $wifiList")
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        wifiList.forEachIndexed { index, wifiDto ->
           Text(text = wifiDto?.ip_addr ?: "null")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WifiListBoxPreview() {
}

// 위치정보 가져오는 함수
@Composable
fun getCurrentLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
    // 라이프 사이클 체크
    val lifecycleViewModel: LifeCycleViewModel = hiltViewModel()
    val isAppInForeground by lifecycleViewModel.isAppInForeground.collectAsState()
    if (isAppInForeground) {
        // 앱이 포그라운드에 있음
        Log.d("getCurrentLocation", "앱이 포그라운드에 있음")
    } else {
        // 앱이 백그라운드에 있음
        Log.d("getCurrentLocation", "앱이 백그라운드에 있음")
    }
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

@RequiresApi(Build.VERSION_CODES.Q)
fun connectToWifiNetwork(context: Context,wifiManager: WifiManager, ssid: String, password: String) {
    val specifier = WifiNetworkSpecifier.Builder()
        .setSsid(ssid)
        .setWpa2Passphrase(password) // 또는 필요에 따라서 WPA3 등을 선택할 수 있습니다.
        .build()

    val suggestion = WifiNetworkSuggestion.Builder()
        .setSsid(ssid)
        .setWpa2Passphrase(password)
        .setPriority(1) // 연결 우선순위 설정 (낮은 숫자가 더 높은 우선순위)
        .setIsAppInteractionRequired(false) // 앱과의 상호작용이 필요한 경우 true로 설정
        .build()

    val suggestionsList = listOf(suggestion)

    val status = wifiManager.addNetworkSuggestions(suggestionsList)
    if (status == WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
        // 연결 제안이 성공적으로 추가된 경우
        Log.d("WifiListBox", "연결 제안이 성공적으로 추가된 경우")
        // 연결 시도
        val networkSpecifier = WifiNetworkSpecifier.Builder()
            .setSsid(ssid)
            .setWpa2Passphrase(password)
            .build()

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(networkSpecifier)
            .build()

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // Wi-Fi 연결 성공
                Log.d("WifiListBox", "Wi-Fi 연결 성공")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    connectivityManager.bindProcessToNetwork(network)
                }
                startWifiSettingsActivity(context)


            }

            override fun onUnavailable() {
                super.onUnavailable()
                // Wi-Fi 연결 실패
                Log.d("WifiListBox", "Wi-Fi 연결 실패")
            }
        }

        connectivityManager.requestNetwork(request, networkCallback)

    } else {
        Log.d("WifiListBox", "연결 제안이 성공적으로 추가된 경우")
        // 연결 제안 추가 실패 시 처리
    }
}
fun startWifiSettingsActivity(context: Context) {
    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
    context.startActivity(intent)
}