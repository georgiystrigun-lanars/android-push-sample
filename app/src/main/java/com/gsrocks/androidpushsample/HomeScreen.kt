package com.gsrocks.androidpushsample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.gsrocks.androidpushsample.ui.theme.AndroidPushSampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { Firebase.analytics.logEvent("log_button_click", null) }) {
                Text(text = "Log Event")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AndroidPushSampleTheme {
        HomeScreen()
    }
}
