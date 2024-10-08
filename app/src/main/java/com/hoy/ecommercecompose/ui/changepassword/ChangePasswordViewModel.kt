package com.hoy.ecommercecompose.ui.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoy.ecommercecompose.R
import com.hoy.ecommercecompose.common.Resource
import com.hoy.ecommercecompose.domain.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val repository: FirebaseAuthRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordContract.UiState())
    val uiState: StateFlow<ChangePasswordContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ChangePasswordContract.UiEffect>() }
    val uiEffect: Flow<ChangePasswordContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(action: ChangePasswordContract.UiAction) {
        when (action) {
            is ChangePasswordContract.UiAction.ChangePassword -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }

            is ChangePasswordContract.UiAction.ChangeConfirmPassword -> {
                _uiState.value = _uiState.value.copy(confirmPassword = action.confirmPassword)
            }

            is ChangePasswordContract.UiAction.ResetPassword -> {
                viewModelScope.launch {
                    resetPassword()
                }
            }

            is ChangePasswordContract.UiAction.ChangeCurrentPassword -> {
                _uiState.value = _uiState.value.copy(currentPassword = action.currentPassword)
            }
        }
    }

    private suspend fun resetPassword() {
        val currentState = _uiState.value
        if (currentState.password != currentState.confirmPassword) {
            _uiState.value = _uiState.value.copy(errorMessage = R.string.passwords_not_matching)
            return
        }
        repository.changePassword(currentState.currentPassword, currentState.password)
            .onStart { updateUiState { copy(isLoading = true) } }
            .onCompletion { updateUiState { copy(isLoading = false) } }
            .collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiEffect.send(ChangePasswordContract.UiEffect.ShowToast(R.string.password_changed_message))
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(errorMessage = 0)
                    }
                }
            }
    }

    private fun updateUiState(block: ChangePasswordContract.UiState.() -> ChangePasswordContract.UiState) {
        _uiState.update(block)
    }
}
