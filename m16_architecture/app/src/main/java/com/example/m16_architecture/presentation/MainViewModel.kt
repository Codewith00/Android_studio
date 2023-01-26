package com.example.m16_architecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m16_architecture.domain.GetUsefulActivityUseCase
import com.example.m16_architecture.entity.UsefulActivity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()

    private val listSearchText = mutableListOf<UsefulActivity>()

    private val _stateIdea = MutableStateFlow<String?>(null)
    val stateIdea = _stateIdea.asStateFlow()

    private val _webLink = MutableStateFlow<String?>(null)
    val webLink = _webLink.asStateFlow()

    fun reloadUsefulActivity() {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
            val currentBody = getUsefulActivityUseCase.execute()
            _stateIdea.value = currentBody.activity
            if (currentBody.link.isNotEmpty()) _webLink.value = currentBody.link
            else _webLink.value = null
            listSearchText.add(currentBody)}catch (e: Error){
                _state.value = State.Error(e.message.toString())
            }
            _state.value = State.Success
        }
    }

    fun restoreListSaveSearch(): String {
        var text = ""
        listSearchText.forEachIndexed { index, usefulActivity ->
            text += "$index. - ${usefulActivity.activity}\n"
            if (usefulActivity.link.isNotEmpty()) text += "${usefulActivity.link}\n"
        }
        return text
    }

    sealed class State {
        object Loading : State()
        object Success : State()
        data class Error(val message: String) : State()
    }
}