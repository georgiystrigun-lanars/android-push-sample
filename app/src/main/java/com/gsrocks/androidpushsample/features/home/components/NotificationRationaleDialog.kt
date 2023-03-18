package com.gsrocks.androidpushsample.features.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gsrocks.androidpushsample.ui.theme.AndroidPushSampleTheme

@Composable
fun NotificationRationaleDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onConfirm) {
                Text("Allow")
            }
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text("Now now")
            }
        },
        title = {
            Text("Allow notifications?")
        },
        text = {
            Text("We need this permission to send you some useful notifications.")
        }
    )
}

@Preview
@Composable
fun NotificationRationaleDialogPreview() {
    AndroidPushSampleTheme {
        NotificationRationaleDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}
