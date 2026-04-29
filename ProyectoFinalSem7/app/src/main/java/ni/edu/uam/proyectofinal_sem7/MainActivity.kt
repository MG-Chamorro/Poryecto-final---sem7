package ni.edu.uam.proyectofinal_sem7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.proyectofinal_sem7.ui.theme.FlowItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowItTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlowItAppNavigation()
                }
            }
        }
    }
}

@Composable
fun FlowItAppNavigation() {
    // El controlador que maneja el estado de las pantallas
    val navController = rememberNavController()

    // El NavHost define el "mapa" de tus pantallas
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("registro") {
            RegisterScreen(navController = navController)
        }

        composable("perfil") {
            ProfileScreen(navController = navController)
        }

        composable("formulario") {
            FormScreen(navController = navController)
        }

        composable("resultados") {
            ResultScreen(navController = navController)
        }
    }
}