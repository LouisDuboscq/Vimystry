package com.lduboscq.vimystry.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.lduboscq.vimystry.android.home.HomeScreen
import com.lduboscq.vimystry.android.ui.VimystryTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VimystryTheme {
                MainLayout()
            }
        }
    }
}

sealed class Screen(val title: String) {
    object Home : Screen("Home")
}

@Composable
fun MainLayout() {
    Scaffold { paddingValues ->
        HomeScreen(paddingValues)
    }
}
