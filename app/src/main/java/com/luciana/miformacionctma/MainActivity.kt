package com.luciana.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luciana.miformacionctma.ui.AppNavigation
import com.luciana.miformacionctma.ui.theme.MiFormacionCTMATheme
import com.luciana.miformacionctma.viewmodel.ActividadViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiFormacionCTMATheme {
                val viewModel: ActividadViewModel = viewModel(
                    factory = ActividadViewModel.factory(application)
                )
                AppNavigation(viewModel)
            }
        }
    }
}
