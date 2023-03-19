package com.gsrocks.androidpushsample.features.home.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.androidpushsample.data.PushRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pushRepository: PushRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val actionChannel = Channel<HomeScreenAction>()
    val actionFlow = actionChannel.receiveAsFlow()

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

    fun onPushTitleChange(title: String) {
        _uiState.update {
            it.copy(pushTitle = title)
        }
    }

    fun onPushBodyChange(body: String) {
        _uiState.update {
            it.copy(pushBody = body)
        }
    }

    fun requestPush() {
        val title = uiState.value.pushTitle
        val body = uiState.value.pushBody
        if (title.isNotBlank() || body.isNotBlank()) {
            viewModelScope.launch {
                val result = pushRepository.requestPush(title, body)
                result.onFailure { failure ->
                    actionChannel.send(
                        HomeScreenAction.ShowError(
                            failure.localizedMessage ?: "Something went wrong"
                        )
                    )
                }
            }
        }
    }
}

sealed class HomeScreenAction {
    data class ShowError(val message: String) : HomeScreenAction()
}
