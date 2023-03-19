package com.gsrocks.androidpushsample.features.home

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.gsrocks.androidpushsample.common.clearFocus
import com.gsrocks.androidpushsample.features.home.components.NotificationRationaleDialog
import com.gsrocks.androidpushsample.features.home.state.HomeScreenAction
import com.gsrocks.androidpushsample.features.home.state.HomeScreenUiState
import com.gsrocks.androidpushsample.features.home.state.HomeViewModel
import com.gsrocks.androidpushsample.ui.theme.AndroidPushSampleTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val notificationPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(notificationPermissionState.status) {
        if (!notificationPermissionState.status.isGranted) {
            if (notificationPermissionState.status.shouldShowRationale) {
                viewModel.showNotificationRationaleDialog()
            } else {
                notificationPermissionState.launchPermissionRequest()
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.actionFlow.collect { action ->
            when (action) {
                is HomeScreenAction.ShowError -> snackbarHostState.showSnackbar(
                    action.message,
                    withDismissAction = true
                )
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onDismissNotificationRationale = viewModel::closeNotificationRationaleDialog,
        onConfirmNotificationRationale = {
            viewModel.closeNotificationRationaleDialog()
            notificationPermissionState.launchPermissionRequest()
        },
        onPushTitleChange = viewModel::onPushTitleChange,
        onPushBodyChange = viewModel::onPushBodyChange,
        onRequestPush = viewModel::requestPush
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    snackbarHostState: SnackbarHostState,
    onDismissNotificationRationale: () -> Unit,
    onConfirmNotificationRationale: () -> Unit,
    onPushTitleChange: (String) -> Unit,
    onPushBodyChange: (String) -> Unit,
    onRequestPush: () -> Unit
) {
    if (uiState.showNotificationRationale) {
        NotificationRationaleDialog(
            onDismiss = onDismissNotificationRationale,
            onConfirm = onConfirmNotificationRationale
        )
    }

    Scaffold(
        modifier = Modifier
            .imePadding()
            .clearFocus(),
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                    dismissActionContentColor = MaterialTheme.colorScheme.error
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = uiState.pushTitle,
                label = { Text("Title") },
                onValueChange = onPushTitleChange
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.pushBody,
                label = { Text("Body") },
                onValueChange = onPushBodyChange
            )

            Spacer(Modifier.height(16.dp))

            Button(onRequestPush) {
                Text("Request push")
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
            snackbarHostState = SnackbarHostState(),
            onDismissNotificationRationale = {},
            onConfirmNotificationRationale = {},
            onPushTitleChange = {},
            onPushBodyChange = {},
            onRequestPush = {}
        )
    }
}
