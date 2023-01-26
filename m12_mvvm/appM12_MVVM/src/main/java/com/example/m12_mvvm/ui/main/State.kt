package com.example.m12_mvvm.ui.main

sealed class State {
    object Loading : State()
    data class SuccessNothing(val message: String) : State()
    data class SuccessResult(val position: Int, val message: String) : State()
    object Ready : State()
    data class NotReady(val message: String) : State()
}
