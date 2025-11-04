package com.kishibashi.androidapp.tutorial.architecture.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.NavGraph
import com.kishibashi.androidapp.tutorial.architecture.compose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                NavGraph()
            }
        }
    }
}
