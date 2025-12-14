package com.example.truemark

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.truemark.feature.presentation.auth.sign_in.SignInScreen
import com.example.truemark.feature.presentation.auth.sign_up.SignUpScreen
import com.example.truemark.feature.presentation.biometric.BiometricScreen
import com.example.truemark.feature.presentation.camera.CameraScreen
import com.example.truemark.feature.presentation.history.HistoryScreen
import com.example.truemark.feature.presentation.home.HomeScreen
import com.example.truemark.feature.presentation.location.LocationScreen
import com.example.truemark.feature.presentation.profile.ProfileScreen
import com.example.truemark.feature.presentation.routes.Routes
import com.example.truemark.ui.theme.TrueMarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrueMarkTheme(
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.SIGN_IN
    ) {

        composable(Routes.SIGN_IN) {
            SignInScreen(navController)
        }

        composable(Routes.SIGN_UP) {
            SignUpScreen(navController)
        }

        composable(Routes.BIOMETRIC) {
            BiometricScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }

        composable(Routes.HISTORY) {
            HistoryScreen(navController)
        }

        composable(
            route = Routes.LOCATION,
            arguments = listOf(
                navArgument("status") { type = NavType.BoolType },
                navArgument("dateTime") { type = NavType.LongType }
            )
        ) {
            val status = navBackStackEntry?.arguments?.getBoolean("status") ?: false
            val dateTime = navBackStackEntry?.arguments?.getLong("dateTime")

            LocationScreen(
                navController = navController,
                status = status,
                dateTime = dateTime
            )
        }

        composable(
            route = Routes.CAMERA,
            arguments = listOf(
                navArgument("status") { type = NavType.BoolType },
                navArgument("dateTime") { type = NavType.LongType },
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType }
            )
        ) {
            val status = navBackStackEntry?.arguments?.getBoolean("status") ?: false
            val dateTime = navBackStackEntry?.arguments?.getLong("dateTime")
            val latitude = navBackStackEntry?.arguments?.getString("latitude")?.toDouble()
            val longitude = navBackStackEntry?.arguments?.getString("longitude")?.toDouble()

            CameraScreen(
                navController = navController,
                status = status,
                dateTime = dateTime,
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TrueMarkTheme {
        App()
    }
}
