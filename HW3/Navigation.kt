package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.myapplication.ui.theme.SampleData


@Composable
fun NavController() {
    val db = Room.databaseBuilder(
        LocalContext.current,
        AppDatabase::class.java, "AppDatabase"
    ).allowMainThreadQueries().build()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "starting page") {
        composable("starting page") {
            StartingPage(
                db,
                onNavigateToChat = {
                    navController.navigate("chat")
                }
            )
        }

        composable("chat") {
            Conversation(
                SampleData.conversationSample,
                db,
                onNavigateToFrontPage = {
                    navController.navigate("starting page") {
                        popUpTo("starting page") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
