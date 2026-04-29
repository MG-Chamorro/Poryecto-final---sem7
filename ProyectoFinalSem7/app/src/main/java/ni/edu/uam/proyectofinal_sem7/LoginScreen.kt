package ni.edu.uam.proyectofinal_sem7

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// 1. Definición de colores globales (puedes moverlos a tu Theme.kt más adelante)
val FlowitDarkGreen = Color(0xFF546B41)   // Primario / Texto
val FlowitMedGreen = Color(0xFF99AD7A)    // Botón Principal
val FlowitBeige = Color(0xFFDCCCAC)       // Bordes / Detalles
val FlowitOffWhite = Color(0xFFFFF8EC)    // Fondo Principal

// --- PANTALLA DE INICIO DE SESIÓN ---
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FlowitOffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Título
            Text(
                text = "Flowit",
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = FlowitDarkGreen
            )
            Text(
                text = "Inicia sesión para continuar",
                fontSize = 16.sp,
                color = FlowitDarkGreen
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Campo: Usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FlowitMedGreen,
                    unfocusedBorderColor = FlowitBeige,
                    focusedLabelColor = FlowitDarkGreen,
                    unfocusedLabelColor = FlowitDarkGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FlowitDarkGreen) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FlowitMedGreen,
                    unfocusedBorderColor = FlowitBeige,
                    focusedLabelColor = FlowitDarkGreen,
                    unfocusedLabelColor = FlowitDarkGreen
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Botón Principal - Iniciar Sesión (Navega al Perfil)
            Button(
                onClick = {
                    // Al dar clic, navegamos a la pantalla "perfil"
                    navController.navigate("perfil")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FlowitMedGreen,
                    contentColor = FlowitOffWhite
                )
            ) {
                Text("Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para ir a Registro
            TextButton(onClick = { navController.navigate("registro") }) {
                Text(
                    "¿No tienes cuenta? Regístrate aquí",
                    color = FlowitDarkGreen,
                    fontSize = 14.sp
                )
            }
        }
    }
}

// --- PANTALLA DE REGISTRO ---
@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FlowitOffWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Crear Cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = FlowitDarkGreen
            )
            Text(
                text = "Únete a Flowit",
                fontSize = 14.sp,
                color = FlowitDarkGreen
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = FlowitDarkGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellido") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FlowitDarkGreen) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Al registrarse exitosamente, mandamos al login o al perfil
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FlowitMedGreen, contentColor = FlowitOffWhite)
            ) {
                Text("Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.popBackStack() }) { // Regresa a la pantalla anterior (Login)
                Text(
                    "¿Ya tienes cuenta? Inicia sesión",
                    color = FlowitDarkGreen,
                    fontSize = 14.sp
                )
            }
        }
    }
}