package com.gsrocks.androidpushsample.features.home

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.gsrocks.androidpushsample.features.home.components.NotificationRationaleDialog
import com.gsrocks.androidpushsample.features.home.state.HomeScreenUiState
import com.gsrocks.androidpushsample.features.home.state.HomeViewModel
import com.gsrocks.androidpushsample.ui.theme.AndroidPushSampleTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = viewModel()
) {
    val notificationPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(notificationPermissionState.status) {
        if (!notificationPermissionState.status.isGranted) {
            if (notificationPermissionState.status.shouldShowRationale) {
                homeViewModel.showNotificationRationaleDialog()
            } else {
                notificationPermissionState.launchPermissionRequest()
            }
        }
    }

    val uiState by homeViewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onDismissNotificationRationale = homeViewModel::closeNotificationRationaleDialog,
        onConfirmNotificationRationale = {
            homeViewModel.closeNotificationRationaleDialog()
            notificationPermissionState.launchPermissionRequest()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    onDismissNotificationRationale: () -> Unit,
    onConfirmNotificationRationale: () -> Unit
) {
    if (uiState.showNotificationRationale) {
        NotificationRationaleDialog(
            onDismiss = onDismissNotificationRationale,
            onConfirm = onConfirmNotificationRationale
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { }) {
                Text(text = "Log Event")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AndroidPushSampleTheme {
        HomeScreen(
            uiState = HomeScreenUiState(),
            onDismissNotificationRationale = {},
            onConfirmNotificationRationale = {}
        )
    }
}
