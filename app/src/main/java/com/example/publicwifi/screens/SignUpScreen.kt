package com.example.publicwifi.screens

//import android.view.Surface
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.core.content.pm.ShortcutInfoCompat
import com.example.publicwifi.R
import com.example.publicwifi.components.ButtonComponent
//import com.example.publicwifi.components.ButtonComponent
import com.example.publicwifi.components.CheckboxComponent
import com.example.publicwifi.components.ClickableLoginTextComponent
import com.example.publicwifi.components.DividerTextComponent
import com.example.publicwifi.components.HeadingTextComponent
import com.example.publicwifi.components.MyTextFieldComponent
import com.example.publicwifi.components.NormalTextComponent
import com.example.publicwifi.navigation.PostOfficeAppRouter
import com.example.publicwifi.navigation.Screen
import com.example.publicwifi.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(viewModel: AuthViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            NormalTextComponent(value = stringResource(id = R.string.hello))
            HeadingTextComponent(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(20.dp))

            MyTextFieldComponent(labelValue = stringResource(id = R.string.id))
            MyTextFieldComponent(labelValue = stringResource(id = R.string.passwd))
            MyTextFieldComponent(labelValue = stringResource(id = R.string.phone_number))

            CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                onTextSelected = {
                    PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                })

            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(
                value = stringResource(id = R.string.register)
            )

            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                PostOfficeAppRouter.navigateTo(Screen.LoginScreen)

            })

        }
        ButtonComponent(value = "회원가입") {
            AuthViewModel.loginUser(userid, passwd)
        }
        
    }

}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}