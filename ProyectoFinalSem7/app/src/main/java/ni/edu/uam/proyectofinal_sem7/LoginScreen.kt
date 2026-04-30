package ni.edu.uam.proyectofinal_sem7

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// --- SIMULACIÓN DE BASE DE DATOS ---
data class UserProfile(val email: String, val name: String, val user: String, val pass: String)

object MockDatabase {
    val users = mutableListOf(
        UserProfile("admin@flowit.com", "Admin", "admin", "1234")
    )
}

// Colores Flowit
val FlowitDarkGreen = Color(0xFF546B41)
val FlowitMedGreen = Color(0xFF99AD7A)
val FlowitBeige = Color(0xFFDCCCAC)
val FlowitOffWhite = Color(0xFFFFF8EC)

// --- PANTALLA DE INICIO DE SESIÓN ---
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = FlowitOffWhite) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(text = "Flowit", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = FlowitDarkGreen)
            Text(text = "Inicia sesión para continuar", fontSize = 16.sp, color = FlowitDarkGreen)

            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
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
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    // Validaciones básicas de login
                    if (username.length <= 2) {
                        Toast.makeText(context, "El usuario debe tener más de 2 caracteres", Toast.LENGTH_SHORT).show()
                    } else if (password.length <= 3) {
                        Toast.makeText(context, "La contraseña debe tener más de 3 caracteres", Toast.LENGTH_SHORT).show()
                    } else {
                        val validUser = MockDatabase.users.find { it.user == username && it.pass == password }
                        if (validUser != null) {
                            navController.navigate("perfil") { popUpTo("login") { inclusive = true } }
                        } else {
                            Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FlowitMedGreen, contentColor = FlowitOffWhite)
            ) {
                Text("Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            TextButton(onClick = { navController.navigate("registro") }) {
                Text("¿No tienes cuenta? Regístrate aquí", color = FlowitDarkGreen)
            }
        }
    }
}

// --- PANTALLA DE REGISTRO CON VALIDACIÓN DE EMAIL ---
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = FlowitOffWhite) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Crear Cuenta", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = FlowitDarkGreen)
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name, onValueChange = { name = it },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username, onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FlowitDarkGreen) },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password, onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FlowitDarkGreen) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // --- NUEVA LÓGICA DE VALIDACIÓN ---

                    // 1. Validar formato de Gmail / Email (debe contener @ y .)
                    if (!email.contains("@") || !email.contains(".")) {
                        Toast.makeText(context, "El correo debe ser válido (ejemplo@gmail.com)", Toast.LENGTH_SHORT).show()
                    }
                    // 2. Validar longitud del usuario (mínimo 3 letras)
                    else if (username.length <= 2) {
                        Toast.makeText(context, "El usuario debe tener más de 2 letras", Toast.LENGTH_SHORT).show()
                    }
                    // 3. Validar longitud de contraseña (mínimo 4 caracteres)
                    else if (password.length <= 3) {
                        Toast.makeText(context, "La contraseña debe tener más de 3 dígitos", Toast.LENGTH_SHORT).show()
                    }
                    // 4. Validar que todos los campos estén llenos
                    else if (name.isEmpty()) {
                        Toast.makeText(context, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show()
                    }
                    // 5. Validar si el usuario ya existe
                    else if (MockDatabase.users.any { it.user == username }) {
                        Toast.makeText(context, "Este usuario ya existe", Toast.LENGTH_SHORT).show()
                    }
                    // 6. Si todo está correcto, registrar
                    else {
                        MockDatabase.users.add(UserProfile(email, name, username, password))
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        navController.navigate("login") { popUpTo("registro") { inclusive = true } }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FlowitMedGreen)
            ) {
                Text("Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { navController.popBackStack() }) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = FlowitDarkGreen)
            }
        }
    }
}