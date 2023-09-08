package com.example.publicwifi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.publicwifi.R
import com.example.publicwifi.components.ButtonComponent
import com.example.publicwifi.components.ClickableLoginTextComponent
import com.example.publicwifi.components.DividerTextComponent
import com.example.publicwifi.components.HeadingTextComponent
import com.example.publicwifi.components.MyTextFieldComponent
import com.example.publicwifi.components.NormalTextComponent
import com.example.publicwifi.components.UnderLinedTextComponent
import com.example.publicwifi.navigation.PostOfficeAppRouter
import com.example.publicwifi.navigation.Screen

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            NormalTextComponent(value = stringResource(id = R.string.login))
            HeadingTextComponent(value = stringResource(id = R.string.welcome))

            Spacer(modifier = Modifier.height(20.dp))

            MyTextFieldComponent(labelValue = stringResource(id = R.string.id))
            MyTextFieldComponent(labelValue = stringResource(id = R.string.passwd))

            Spacer(modifier = Modifier.height(40.dp))

            UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))

            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(value = stringResource(id = R.string.login))

            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)

            })

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}