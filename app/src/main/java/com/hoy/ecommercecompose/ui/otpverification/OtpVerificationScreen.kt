package com.hoy.ecommercecompose.ui.sendcode

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.login.LoginContract
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun OtpVerificationScreen(
    onBackClick: () -> Unit,
    uiState: LoginContract.LoginUiState,
    onAction: (LoginContract.LoginUiAction) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(48.dp)
                .border(
                    BorderStroke(1.dp, LocalColors.current.primary),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                modifier = Modifier.size(38.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "OTP Verification",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the verification code we just sent on your email address.",
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OtpBox()
            OtpBox()
            OtpBox()
            OtpBox()
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Verify",
            onClick = { })

        Spacer(modifier = Modifier.height(400.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Didn't received code?",
                fontWeight = FontWeight.Thin,
                fontSize = 18.sp,
            )
            Text(
                text = "Resend",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = LocalColors.current.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtpVerificationScreenPrew() {
    OtpVerificationScreen(
        onBackClick = { },
        uiState = LoginContract.LoginUiState(),
        onAction = { },
        navController = rememberNavController()
    )
}

@Composable
fun OtpBox() {
    TextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .size(48.dp)
            .border(
                BorderStroke(1.dp, LocalColors.current.primary),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
        ),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 24.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        ),
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}
