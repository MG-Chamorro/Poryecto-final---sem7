package ni.edu.uam.proyectofinal_sem7

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Paleta de colores escolar
val VerdeOscuro = Color(0xFF2D5A2D)
val VerdePrimario = Color(0xFF4A7C59)
val VerdeClaro = Color(0xFF8BBE8C)
val Beige = Color(0xFFF5E8C7)
val Crema = Color(0xFFFFFBF0)
val GrisClaro = Color(0xFFF8F9FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var editMode by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("María González") }
    var email by remember { mutableStateOf("maria.gonzalez@email.com") }
    var correoInstitucional by remember { mutableStateOf("mgonzalez@uam.edu.ni") }
    var telefono by remember { mutableStateOf("+505 8888 1234") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "👤 Mi Perfil",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Crema,
                        letterSpacing = 2.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = VerdePrimario
                ),
                actions = {
                    IconButton(onClick = { editMode = !editMode }) {
                        Icon(
                            imageVector = if (editMode) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = "Editar Perfil",
                            tint = Crema
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("formulario") },
                containerColor = VerdePrimario,
                contentColor = Crema
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Ir al formulario")
            }
        }
    ) { padding ->
        ProfileContent(
            modifier = Modifier.padding(padding),
            editMode = editMode,
            nombre = nombre,
            onNombreChange = { nombre = it },
            email = email,
            onEmailChange = { email = it },
            correoInstitucional = correoInstitucional,
            onCorreoInstitucionalChange = { correoInstitucional = it },
            telefono = telefono,
            onTelefonoChange = { telefono = it },
            navController = navController
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    editMode: Boolean,
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    correoInstitucional: String,
    onCorreoInstitucionalChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
    navController: NavController
) {
    // ScrollState permite deslizar la pantalla si el contenido es muy largo
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        ProfileHeader(nombre)
        Spacer(Modifier.height(24.dp))

        ProfileFormCard(
            editMode, nombre, onNombreChange,
            email, onEmailChange,
            correoInstitucional, onCorreoInstitucionalChange,
            telefono, onTelefonoChange
        )

        Spacer(Modifier.height(24.dp))
        ProfileCourses()
        Spacer(Modifier.height(24.dp))
        ProfileSettings(navController)
    }
}

@Composable
fun ProfileHeader(nombre: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = VerdePrimario),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(GrisClaro)
                    .shadow(8.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nombre.take(1).uppercase(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePrimario
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = nombre,
                fontSize = 20.sp,
                color = Crema,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "📚 Estudiante - Ingeniería en Sistemas",
                fontSize = 14.sp,
                color = Crema,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProfileFormCard(
    editMode: Boolean,
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    correoInstitucional: String,
    onCorreoInstitucionalChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Beige)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(
                text = "📋 Información Personal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimario,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChange,
                label = { Text("👤 Nombre Completo") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("📧 Correo Personal") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = correoInstitucional,
                onValueChange = onCorreoInstitucionalChange,
                label = { Text("🎓 Correo Institucional") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = onTelefonoChange,
                label = { Text("📱 Teléfono") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )
        }
    }
}

// Estructura de datos para las clases
data class Curso(val nombre: String, val docente: String, val creditos: String, val horario: String)

@Composable
fun ProfileCourses() {
    val cursos = listOf(
        Curso("Programación Orientada a Objetos II", "Ing. Roberto M.", "4 Créditos", "Lun-Mié 10:00 AM"),
        Curso("Bases de Datos Avanzadas", "MSc. Ana López", "4 Créditos", "Mar-Jue 08:00 AM"),
        Curso("Ingeniería de Software", "Dr. Carlos Ruiz", "3 Créditos", "Vie 09:00 AM")
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VerdeClaro)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(
                text = "💻 Mis Clases (Ing. en Sistemas)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeOscuro,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            cursos.forEach { curso ->
                var expanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { expanded = !expanded },
                    colors = CardDefaults.cardColors(containerColor = Crema),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = curso.nombre,
                                fontWeight = FontWeight.Bold,
                                color = VerdeOscuro,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandir detalles",
                                tint = VerdePrimario
                            )
                        }

                        if (expanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "👨‍🏫 Docente: ${curso.docente}", color = VerdePrimario, fontSize = 14.sp)
                            Text(text = "⭐ Créditos: ${curso.creditos}", color = VerdePrimario, fontSize = 14.sp)
                            Text(text = "🕒 Horario: ${curso.horario}", color = VerdePrimario, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileSettings(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Beige)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = "⚙️ Configuración",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimario,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProfileSettingItem(
                icon = Icons.Default.Notifications,
                title = "Notificaciones",
                subtitle = "Recibir alertas de la plataforma",
                onClick = {}
            )

            ProfileSettingItem(
                icon = Icons.Default.Lock,
                title = "Privacidad",
                subtitle = "Datos personales",
                onClick = {}
            )

            ProfileSettingItem(
                icon = Icons.Default.Info,
                title = "Ayuda",
                subtitle = "Soporte técnico estudiantil",
                onClick = {}
            )

            Divider(color = VerdeClaro, modifier = Modifier.padding(vertical = 8.dp))

            // Botón interactivo de Cerrar Sesión
            ProfileSettingItem(
                icon = Icons.Default.ExitToApp,
                title = "Cerrar Sesión",
                subtitle = "Salir de la cuenta",
                onClick = {
                    navController.navigate("login") {
                        // Limpia el historial para no regresar al perfil si el usuario presiona "Atrás"
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileSettingItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = VerdePrimario
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = VerdePrimario
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = VerdeClaro
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = VerdeClaro,
            modifier = Modifier.size(20.dp)
        )
    }
}