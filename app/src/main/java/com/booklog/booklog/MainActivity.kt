package com.booklog.booklog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.booklog.booklog.navigation.AppNavigation
import com.booklog.booklog.ui.theme.BookLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookLogTheme {
                AppNavigation()
            }
        }
    }
}