package com.example.miformacionctma.ui.viewmodel

sealed interface RefreshUiState {

    data object Idle : RefreshUiState

    data object Running : RefreshUiState

    data class Success(
        val at: String
    ) : RefreshUiState

    data class Failed(
        val message: String
    ) : RefreshUiState
}