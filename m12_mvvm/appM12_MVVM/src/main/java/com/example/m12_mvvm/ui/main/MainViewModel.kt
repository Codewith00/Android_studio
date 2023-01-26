package com.example.m12_mvvm.ui.main

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m12_mvvm.R
import com.example.m12_mvvm.textStorage.News
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.NotReady("Введите 3 или больше символов"))
    val state = _state.asStateFlow()

    private val _inputText = Channel<String?>()
    val inputText = _inputText.receiveAsFlow()

    private val randomContains = News()
    private val listViewBlocs = mutableListOf<View>()
    private val listLogoText = mutableListOf<String>()
    private val listContainsText = mutableListOf<String>()
    private var checkId = 0


    fun onChangeListener(text: String) {
        viewModelScope.launch {
            if (text.length >= 3) {
                _state.value = State.Ready
            } else {
                _state.value = State.NotReady("Введите 3 или больше символов")
            }
        }
    }

    fun onClick(text: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            search(text.replace("\n", ""))
        }
    }

    fun addCardToScroll(context: Context): View {
        val blockView = View.inflate(context, R.layout.container, null)
        val containerImage = blockView.findViewById<ImageView>(R.id.imageContainer)
        val logoImage = blockView.findViewById<ImageView>(R.id.imageLogo)
        val textContainer = blockView.findViewById<TextView>(R.id.textContainer)
        val logoText = blockView.findViewById<TextView>(R.id.textLogo)
        logoText.text = randomContains.textsLogo.random()
        textContainer.text = randomContains.textsContainer.random()
        logoImage.setImageResource(randomContains.imageLogo.random())
        containerImage.setImageResource(randomContains.imageContainer.random())
        blockView.id = R.id.container + checkId
        checkId++
        listLogoText.add(logoText.text.toString())
        listContainsText.add(textContainer.text.toString())
        listViewBlocs.add(blockView)
        return blockView
    }

   private suspend fun search(text: String) {
       val resultSearch = repository.getData(text)
       var checkAmountResult = 0
       var position = 0
        if (resultSearch != null) {
            listViewBlocs.forEachIndexed { index, view ->
                if (listLogoText[index].lowercase() == resultSearch.lowercase() ||
                    listContainsText[index].lowercase() == resultSearch.lowercase()) {
                    position = view.y.toInt()
                    checkAmountResult++
                }
            }
            _state.value = State.SuccessResult(position, text)
            _inputText.send("Количество результатов поиска: $checkAmountResult")
        }else{
        _state.value = State.SuccessNothing(text)
       _inputText.send("Ничего не найдено")
    }}


}