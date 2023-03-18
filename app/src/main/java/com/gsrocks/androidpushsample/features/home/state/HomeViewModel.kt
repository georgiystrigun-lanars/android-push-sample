package com.gsrocks.androidpushsample.features.home.state

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun showNotificationRationaleDialog() {
        _uiState.update {
            it.copy(showNotificationRationale = true)
        }
    }

    fun closeNotificationRationaleDialog() {
        _uiState.update {
            it.copy(showNotificationRationale = false)
        }
    }
}
