package com.example.publicwifi.screens

//import android.view.Surface
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.publicwifi.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    openLoginScreen: () -> Unit = {},
) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current

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

            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.id),
                text = authViewModel.userid,
                onTextChange = { authViewModel.setUserId(it) },
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.passwd),
                text = authViewModel.password,
                onTextChange = { authViewModel.setPassword(it) },
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.phone_number),
                text = authViewModel.phoneNumber,
                onTextChange = { authViewModel.setPhoneNumber(it) },
            )

            CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                onTextSelected = {
                })

            Spacer(modifier = Modifier.height(40.dp))

            ButtonComponent(
                value = stringResource(id = R.string.register),
                onClick = {
                    if (authViewModel.userid.isEmpty()
                    ) {
                        Toast.makeText(
                            context,
                            "아이디를 입력해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@ButtonComponent
                    }
                    if (authViewModel.password.isEmpty()
                    ) {
                        Toast.makeText(
                            context,
                            "비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@ButtonComponent
                    }
                    if (authViewModel.phoneNumber.isEmpty()
                    ) {
                        Toast.makeText(
                            context,
                            "전화번호를 입력해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@ButtonComponent
                    }

                    authViewModel.requestSignUpApi(
                        userid = authViewModel.userid,
                        password = authViewModel.password,
                        phoneNumber = authViewModel.phoneNumber
                    )
                    Log.d(
                        "SignUpScreen",
                        "userId: ${authViewModel.userid} password : ${authViewModel.password} phoneNumber : ${authViewModel.phoneNumber}"
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()

            BottomSignUpText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onclick = {
                    authViewModel.setUserId("")
                    authViewModel.setPassword("")
                    authViewModel.setPhoneNumber("")
                    openLoginScreen()
                }
            )
        }
    }
}

@Composable
private fun BottomSignUpText(modifier: Modifier = Modifier, onclick: () -> Unit) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.clickable {
                onclick()
            },
            text = "계정이 이미 있으신가요?",
            fontSize = 17.sp,
        )
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}