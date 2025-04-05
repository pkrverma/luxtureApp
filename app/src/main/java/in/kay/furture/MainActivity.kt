package `in`.kay.furture

import `in`.kay.furture.models.FurnitureModel
import `in`.kay.furture.screens.*
import `in`.kay.furture.ui.theme.FurtureTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint

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

                        composable("splash") {
                            SplashScreen(navController)
                        }

                        composable("home") {
                            HomeScreen(navController, viewModel)
                        }

                        composable("detail") {
                            DetailScreen(viewModel, navController)
                        }

                        composable("checkout") {
                            CheckoutScreen(navController = navController, viewModel = viewModel)

                        }

                        composable("address") {
                            AddressScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
