package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.SampleData

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "messages"
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("messages") {
            ConversationScreen(
                messages = SampleData.conversationSample,
                onNavigateToBack = {
                    navController.navigate("settings") {
                        popUpTo("settings") {
                            inclusive = true
                        }
                    } },
            )
        }

        composable("settings") {
            Settings(
                onNavigateToMessages = { navController.navigate("messages") {
                    popUpTo("messages") {
                        inclusive = true
                    }
                }
                }
            )
        }
    }
}
