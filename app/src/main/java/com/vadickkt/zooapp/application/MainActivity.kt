package com.vadickkt.zooapp.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.vadickkt.zooapp.navigation.MainNavHost
import com.vadickkt.zooapp.ui.theme.ZooAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZooAppTheme {
                val navigationController = rememberNavController()
                MainNavHost(navigationController)
            }
        }
    }
}