package ni.edu.uam.proyectofinal_sem7

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.absoluteValue

// 🎨 Paleta FlowIt Mejorada (Asegúrate de que los nombres no choquen con otras pantallas si los unificas)
val VerdeOscuroForm = Color(0xFF2D5A2D)
val VerdePrimarioForm = Color(0xFF4A7C59)
val VerdeClaroForm = Color(0xFF8BBE8C)
val BeigeForm = Color(0xFFF5E8C7)
val CremaForm = Color(0xFFFFFBF0)
val RojoGasto = Color(0xFFE74C3C)
val GrisClaroForm = Color(0xFFF8F9FA)

// Modelo de datos
data class Movimiento(
    val id: Long,
    val tipo: String,
    val monto: String,
    val descripcion: String,
    val fecha: Long
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController) {
    var showForm by remember { mutableStateOf(true) }

    // El estado de los movimientos se guarda en la pantalla principal
    val movimientos = remember { mutableStateListOf<Movimiento>() }
    val balance = movimientos.sumOf {
        val montoNum = it.monto.toDoubleOrNull() ?: 0.0
        if (it.tipo == "Ingreso") montoNum else -montoNum
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = CremaForm)
                    }
                },
                title = {
                    Text(
                        text = "FlowIt",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CremaForm,
                        letterSpacing = 2.sp
                    )
                },
                actions = {
                    // Botón para ir a los resultados finales
                    TextButton(onClick = { navController.navigate("resultados") }) {
                        Text("Ver Resultados", color = CremaForm, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = VerdePrimarioForm
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showForm = !showForm },
                containerColor = VerdePrimarioForm,
                contentColor = CremaForm
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        containerColor = CremaForm
    ) { padding ->
        FlowItContent(
            modifier = Modifier.padding(padding),
            showForm = showForm,
            movimientos = movimientos,
            balance = balance
        )
    }
}

@Composable
fun FlowItContent(
    modifier: Modifier = Modifier,
    showForm: Boolean,
    movimientos: MutableList<Movimiento>,
    balance: Double
) {
    val context = LocalContext.current
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Ingreso") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header con balance
        BalanceCard(balance = balance)

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario animado
        AnimatedVisibility(
            visible = showForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400)
            ) + expandVertically(expandFrom = Alignment.Top),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            FormularioMovimiento(
                monto = monto,
                onMontoChange = { monto = it },
                descripcion = descripcion,
                onDescripcionChange = { descripcion = it },
                tipo = tipo,
                onTipoChange = { tipo = it },
                onGuardar = {
                    if (monto.isNotEmpty() && descripcion.isNotEmpty()) {
                        val nuevoMovimiento = Movimiento(
                            id = System.currentTimeMillis(),
                            tipo = tipo,
                            monto = monto,
                            descripcion = descripcion,
                            fecha = System.currentTimeMillis()
                        )
                        movimientos.add(nuevoMovimiento)

                        monto = ""
                        descripcion = ""

                        Toast.makeText(context, "✅ Movimiento guardado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de movimientos
        HistorialMovimientos(movimientos = movimientos, onEliminar = { movimiento ->
            movimientos.remove(movimiento)
        })
    }
}

@Composable
fun BalanceCard(balance: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (balance >= 0) VerdeClaroForm else RojoGasto
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Monto Total",
                fontSize = 14.sp,
                color = CremaForm,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${String.format("%.2f", balance.absoluteValue)}",
                fontSize = 28.sp,
                color = CremaForm,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (balance >= 0) "¡Excelente!" else "Revisa tus gastos",
                fontSize = 12.sp,
                color = CremaForm.copy(alpha = 0.9f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioMovimiento(
    monto: String,
    onMontoChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    tipo: String,
    onTipoChange: (String) -> Unit,
    onGuardar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BeigeForm)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "📝 Nuevo Movimiento",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimarioForm,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = monto,
                onValueChange = onMontoChange,
                label = { Text("💵 Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimarioForm,
                    unfocusedBorderColor = VerdeClaroForm,
                    focusedContainerColor = CremaForm,
                    unfocusedContainerColor = BeigeForm
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("📄 Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                maxLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimarioForm,
                    unfocusedBorderColor = VerdeClaroForm,
                    focusedContainerColor = CremaForm,
                    unfocusedContainerColor = BeigeForm
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = tipo == "Ingreso",
                    onClick = { onTipoChange("Ingreso") },
                    label = { Text("📈 Ingreso") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = GrisClaroForm,
                        selectedContainerColor = VerdePrimarioForm,
                        labelColor = VerdePrimarioForm,
                        selectedLabelColor = CremaForm
                    )
                )

                FilterChip(
                    selected = tipo == "Gasto",
                    onClick = { onTipoChange("Gasto") },
                    label = { Text("📉 Gasto") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = GrisClaroForm,
                        selectedContainerColor = RojoGasto,
                        labelColor = RojoGasto,
                        selectedLabelColor = CremaForm
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onGuardar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimarioForm)
            ) {
                Text("💾 Guardar Movimiento", fontSize = 16.sp, color = CremaForm)
            }
        }
    }
}

@Composable
fun HistorialMovimientos(movimientos: List<Movimiento>, onEliminar: (Movimiento) -> Unit) {
    Column {
        Text(
            text = "📋 Historial",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = VerdePrimarioForm,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (movimientos.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BeigeForm),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📭", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No hay movimientos",
                            fontWeight = FontWeight.Medium,
                            color = VerdeClaroForm
                        )
                        Text(
                            text = "¡Agrega tu primer movimiento!",
                            fontSize = 14.sp,
                            color = VerdeClaroForm
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movimientos) { movimiento ->
                    MovimientoCard(movimiento, onEliminar)
                }
            }
        }
    }
}

@Composable
fun MovimientoCard(movimiento: Movimiento, onEliminar: (Movimiento) -> Unit) {
    val color = if (movimiento.tipo == "Ingreso") VerdeClaroForm else RojoGasto

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = BeigeForm),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = color),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (movimiento.tipo == "Ingreso") "📈" else "📉",
                        fontSize = 20.sp,
                        color = CremaForm
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movimiento.descripcion,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = VerdePrimarioForm
                )
                Text(
                    text = "$${movimiento.monto}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            // Implementación funcional del botón eliminar
            IconButton(onClick = { onEliminar(movimiento) }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = RojoGasto
                )
            }
        }
    }
}