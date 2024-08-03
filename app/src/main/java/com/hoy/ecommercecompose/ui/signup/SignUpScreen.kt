package com.hoy.ecommercecompose.ui.signup

import SignUpContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.ui.components.CustomAlertDialog
import com.hoy.ecommercecompose.ui.components.CustomButton
import com.hoy.ecommercecompose.ui.components.CustomTextField
import com.hoy.ecommercecompose.ui.theme.LocalColors

@Composable
fun SignupScreen(
    onBackClick: () -> Unit,
    uiState: SignUpContract.UiState,
    onAction: (SignUpContract.UiAction) -> Unit,
    navController: NavController,
    viewModel: SignUpViewModel,
) {

    LaunchedEffect(uiState.isSignUp) {
        if (uiState.isSignUp) {
            navController.navigate("home") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    if (uiState.signUpError != null) {
        CustomAlertDialog(
            errorMessage = uiState.signUpError,
            onDismiss = { viewModel.clearError() }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.hello_register_text),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            CustomTextField(
                value = uiState.name,
                onValueChange = { onAction(SignUpContract.UiAction.ChangeName(it)) },
                label = stringResource(id = R.string.name_text),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
            )
            CustomTextField(
                value = uiState.surname,
                onValueChange = { onAction(SignUpContract.UiAction.ChangeSurname(it)) },
                label = stringResource(id = R.string.surname_text),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
            )
            CustomTextField(
                value = uiState.email,
                onValueChange = { onAction(SignUpContract.UiAction.ChangeEmail(it)) },
                label = stringResource(id = R.string.email_text),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
            )
            CustomTextField(
                value = uiState.password,
                isPassword = true,
                onValueChange = { onAction(SignUpContract.UiAction.ChangePassword(it)) },
                label = stringResource(id = R.string.password_text),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                text = stringResource(id = R.string.register_text),
                onClick = {
                    viewModel.onAction(SignUpContract.UiAction.SignUpClick)
                }
            )
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                color = LocalColors.current.primary
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignupPreview() {
}
