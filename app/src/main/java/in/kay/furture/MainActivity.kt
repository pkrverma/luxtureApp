package `in`.kay.furture

import `in`.kay.furture.screens.DetailScreen
import `in`.kay.furture.screens.HomeScreen
import `in`.kay.furture.screens.SplashScreen
import `in`.kay.furture.ui.theme.FurtureTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.kay.furture.models.FurnitureModel
import `in`.kay.furture.screens.CheckoutScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FurtureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<SharedViewModel>()
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("home") {
                            HomeScreen(navController, viewModel)
                        }
                        composable("detail") {
                            DetailScreen(viewModel, navController)
                        }
                        composable("splash") {
                            SplashScreen(navController = navController)
                        }
                        composable("checkout") {
                            val furnitureModel =
                                navController.previousBackStackEntry?.savedStateHandle?.get<FurnitureModel>("checkoutItem")
                            if (furnitureModel != null) {
                                CheckoutScreen(navController = navController, item = furnitureModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FurtureTheme {
        Greeting("Android")
    }
}
