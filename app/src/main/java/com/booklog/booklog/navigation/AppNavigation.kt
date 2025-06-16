package com.booklog.booklog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import com.booklog.booklog.ui.home.Book
import com.booklog.booklog.ui.home.HomeScreen
import com.booklog.booklog.ui.addbook.AddBookScreen
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val books = remember { mutableStateListOf<Book>() }

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                books = books,
                onAddBookClick = { navController.navigate(Routes.ADD_BOOK) }
                //onBookClick = { navController.navigate(Routes.HOME) }
            )
        }
        composable(Routes.ADD_BOOK) {
            AddBookScreen(
                onBookAdded = { newBook ->
                    books.add(newBook)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}


