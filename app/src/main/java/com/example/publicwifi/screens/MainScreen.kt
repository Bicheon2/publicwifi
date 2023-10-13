package com.example.publicwifi.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.publicwifi.components.MapComponent
import com.example.publicwifi.ui.theme.Primary
import com.example.publicwifi.ui.theme.Secondary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
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
            WifiMapBox(modifier = Modifier)
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
fun WifiMapBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        MapComponent()
    }
}

@Composable
fun WifiListBox(modifier: Modifier = Modifier) {
}