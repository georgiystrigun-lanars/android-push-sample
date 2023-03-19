package com.gsrocks.androidpushsample.features.home.state

data class HomeScreenUiState(
    val showNotificationRationale: Boolean = false,
    val pushTitle: String = "",
    val pushBody: String = ""
)
