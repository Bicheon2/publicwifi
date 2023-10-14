package com.example.publicwifi.share

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.publicwifi.api.RetrofitHelper
import com.example.publicwifi.api.dto.LoginDtoParams
import com.example.publicwifi.api.dto.SignUpDto
import com.example.publicwifi.api.dto.SignUpDtoParams
import com.example.publicwifi.api.dto.WifiDto
import com.example.publicwifi.api.dto.WifiDtoParams
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import java.util.Locale

@HiltAndroidApp
class ShareApplication : Application() {

    var connectedWifiSSID: String? = null
    var connectedWifiIP: String? = null

    lateinit var fusedLocationClient: FusedLocationProviderClient
    var isRegistered = false

    // retrofit api
    val api = RetrofitHelper.getWifiService()
    override fun onCreate() {
        super.onCreate()
        Log.d("appliocationtest", "onCreate")
        val context = this@ShareApplication

        // 위치정보
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo

                val ssid = wifiInfo.ssid
                val ipAddress = wifiInfo.ipAddress

                val ipString = String.format(
                    "%d.%d.%d.%d",
                    (ipAddress and 0xFF),
                    (ipAddress shr 8 and 0xFF),
                    (ipAddress shr 16 and 0xFF),
                    (ipAddress shr 24 and 0xFF)
                )

                connectedWifiSSID = ssid
                connectedWifiIP = ipString
                Log.d("WifiConnectReceiver", "Connected to WiFi SSID: $ssid")
                Log.d("WifiConnectReceiver", "IP Address: $ipString")

                requestLocation(
                    context = context,
                    callback = { location ->
                        // 위치 정보를 사용하여 원하는 작업을 수행합니다.
                        val latitude = location.latitude
                        val longitude = location.longitude
                        Log.d("WifiConnectReceiver", "위도: $latitude, 경도: $longitude")
                        val address: AddressInfo? =
                            getAddressFromLocation(context, latitude, longitude)
                        if (ssid.isNotEmpty()) {
                            Log.d("WifiConnectReceiver", "ssid: ${ssid}")
                            api.postWifi(
                                WifiDtoParams(
                                    ssid = ssid,
                                    lat = latitude,
                                    lng = longitude,
                                    addr_state = address?.locality ?: "",
                                    addr_city = address?.thoroughfare ?: "",
                                    addr_detail = "${address?.subLocality ?: ""} ${address?.subThoroughfare ?: ""}",
                                    ip_addr = ipString,
                                )
                            ).enqueue(object : retrofit2.Callback<WifiDto?> {
                                override fun onResponse(
                                    call: retrofit2.Call<WifiDto?>,
                                    response: retrofit2.Response<WifiDto?>
                                ) {
                                    if (response.isSuccessful) {
                                        Log.d("authViewModel", "onResponse: ${response.body()}")
                                    } else {
                                    }
                                }
                                override fun onFailure(call: Call<WifiDto?>, t: Throwable) {
                                    Log.d("authViewModel", "onFailure: ${t.message}")
                                }
                            })
                        }
                    },
                    errorCallback = {
                        Log.d("WifiConnectReceiver", "위치정보 사용 불가")
                    }

                )
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("WifiConnectReceiver", "Wi-Fi network connection lost")
                Log.d("WifiConnectReceiver", "lost wifi SSID: $connectedWifiSSID")
                Log.d("WifiConnectReceiver", "lost wifi IP Address: $connectedWifiIP")
                // 네트워크 연결이 끊긴 경우 처리
            }
        }
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    // 위치정보
    fun requestLocation(context: Context, callback: (Location) -> Unit, errorCallback: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        callback(location)
                    } else {
                        errorCallback()
                    }
                }
        } else {
            errorCallback()
        }
    }

    // 주소정보
    fun getAddressFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double
    ): AddressInfo? {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                val components = mutableListOf<String>()

                val featureName = address.featureName
                if (!featureName.isNullOrBlank()) {
                    components.add(featureName)
                }

                val thoroughfare = address.thoroughfare
                if (!thoroughfare.isNullOrBlank()) {
                    components.add(thoroughfare)
                }

                val subThoroughfare = address.subThoroughfare
                if (!subThoroughfare.isNullOrBlank()) {
                    components.add(subThoroughfare)
                }

                val subLocality = address.subLocality
                if (!subLocality.isNullOrBlank()) {
                    components.add(subLocality)
                }

                val locality = address.locality
                if (!locality.isNullOrBlank()) {
                    components.add(locality)
                }

                val subAdminArea = address.subAdminArea
                if (!subAdminArea.isNullOrBlank()) {
                    components.add(subAdminArea)
                }

                val adminArea = address.adminArea
                if (!adminArea.isNullOrBlank()) {
                    components.add(adminArea)
                }

                return AddressInfo(
                    featureName = featureName,
                    adminArea = adminArea,
                    subAdminArea = subAdminArea,
                    locality = locality,
                    subLocality = subLocality,
                    thoroughfare = thoroughfare,
                    subThoroughfare = subThoroughfare,
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return AddressInfo(
            featureName = "",
            adminArea = "",
            subAdminArea = "",
            locality = "",
            subLocality = "",
            thoroughfare = "",
            subThoroughfare = "",
        )
    }

}

data class AddressInfo(
    val featureName: String?,
    val thoroughfare: String?,
    val subThoroughfare: String?,
    val subLocality: String?,
    val locality: String?,
    val subAdminArea: String?,
    val adminArea: String?
)