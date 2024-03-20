package com.example.testapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController


@Composable
fun LoginScreen(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        val (header, log, pass, join) = createRefs()
        var login by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Text(
            text = "Авторизация",
            color = Color.White,
            fontSize = 54.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        ConstraintLayout(
            modifier = Modifier.constrainAs(log) {
                top.linkTo(header.bottom, margin = 300.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            val (editTextPassword, imageView) = createRefs()

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                textStyle = TextStyle(),
                label = { Text("Email") },
                placeholder = { Text("Enter your email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        ConstraintLayout(
            modifier = Modifier.constrainAs(pass) {
                top.linkTo(log.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            val (editTextPassword, imageView) = createRefs()

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(),
                label = { Text("Password") },
                placeholder = { Text("Enter your email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
            )
        }


        Button(
            onClick = { navController.navigate("fragment_main") },
            modifier = Modifier.constrainAs(join) {
                bottom.linkTo(parent.bottom, margin = 28.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(text = "Войти", color = Color.White, fontSize = 32.sp)
        }
    }
}