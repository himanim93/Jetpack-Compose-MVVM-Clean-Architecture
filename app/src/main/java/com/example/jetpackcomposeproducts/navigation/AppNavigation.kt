package com.example.jetpackcomposeproducts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeproducts.login.ui.LoginScreen
import com.example.jetpackcomposeproducts.products.ui.ProductScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppNavigationScreens.LOGIN.name) {

        composable(route = AppNavigationScreens.LOGIN.name) {
            LoginScreen(navigateToProduct = {
                navController.navigate(AppNavigationScreens.PRODUCT.name){
                    popUpTo(AppNavigationScreens.LOGIN.name) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            })
        }

        composable(route = AppNavigationScreens.PRODUCT.name) {
            ProductScreen()
        }
    }
}

enum class AppNavigationScreens {
    LOGIN, PRODUCT
}