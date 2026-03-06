package com.example.spendtracker

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spendtracker.ui.MainViewModel
import com.example.spendtracker.ui.MainViewModelFactory
import com.example.spendtracker.ui.navigation.Screen
import com.example.spendtracker.ui.screens.DashboardScreen
import com.example.spendtracker.ui.screens.SettingsScreen

// Hosts the Jetpack Compose UI and wires ViewModel, navigation, and top-level theming.
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendTrackerAppRoot(application = application)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendTrackerAppRoot(application: Application) {
    val navController = rememberNavController()

    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(application)
    )

    val dashboardState by viewModel.dashboardUiState.collectAsState()
    val thresholdState by viewModel.thresholdUiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Dashboard.route

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when (currentRoute) {
                                Screen.Dashboard.route -> Screen.Dashboard.title
                                Screen.Settings.route -> Screen.Settings.title
                                else -> "Spend Tracker"
                            }
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Dashboard.route,
                        onClick = { navController.navigate(Screen.Dashboard.route) },
                        label = { Text(Screen.Dashboard.title) },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Settings.route,
                        onClick = { navController.navigate(Screen.Settings.route) },
                        label = { Text(Screen.Settings.title) },
                        icon = {}
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Dashboard.route) {
                    DashboardScreen(
                        uiState = dashboardState,
                        onSyncSms = { viewModel.syncSms() },
                        contentPadding = innerPadding
                    )
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        uiState = thresholdState,
                        onDailyChanged = viewModel::onDailyThresholdChanged,
                        onWeeklyChanged = viewModel::onWeeklyThresholdChanged,
                        onMonthlyChanged = viewModel::onMonthlyThresholdChanged,
                        onMessageChanged = viewModel::onCustomMessageChanged,
                        onSaveClicked = { viewModel.saveThresholds() },
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}
