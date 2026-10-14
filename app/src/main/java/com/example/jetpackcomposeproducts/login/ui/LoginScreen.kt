package com.example.jetpackcomposeproducts.login.ui

import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposeproducts.R
import com.example.jetpackcomposeproducts.login.data.remote.model.LoginRequestDTO

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navigateToProduct: () -> Unit) {
    val context = LocalContext.current
    val state = viewModel.loginUiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loginUiEvent.collect { event ->
            when (event) {
                LoginUiEvent.NavigateToProduct -> {
                    navigateToProduct.invoke()
                }

                is LoginUiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        context.resources.getString(event.message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    LoginContent(state = state.value, onLoginClick = { username, password ->
        viewModel.login(
            LoginRequestDTO(
                username,
                password
            )
        )
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(state: LoginUiState, onLoginClick: (String, String) -> Unit) {
    val usernameState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    val validationError = state as? LoginUiState.LoginValidationError

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = stringResource(R.string.title_login))
                })
            }) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = usernameState.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { usernameState.value = it },
                    isError = validationError?.isUserNameValid == false,
                    supportingText = {
                        if (validationError?.isUserNameValid == false) {
                            Text(text = stringResource(validationError.usernameMessage))
                        }
                    },
                    label = { Text(text = stringResource(R.string.login_username)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = passwordState.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { passwordState.value = it },
                    isError = validationError?.isPasswordValid == false,
                    supportingText = {
                        if (validationError?.isPasswordValid == false) {
                            Text(text = stringResource(validationError.passwordMessage))
                        }
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text(text = stringResource(R.string.login_password)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onLoginClick(usernameState.value, passwordState.value)
                    }) {
                    Text(text = stringResource(R.string.login_button_submit))
                }
            }
        }

        (state as? LoginUiState.Loading)?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginContent(
        state = LoginUiState.LoginValidationError(
            isUserNameValid = false,
            R.string.login_error_username_invalid,
            isPasswordValid = false,
            R.string.login_error_password_invalid
        ),
        { username, password -> })
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginLoadingScreen() {
    LoginContent(
        state = LoginUiState.Loading,
        { username, password -> })
}