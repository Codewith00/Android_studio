package com.example.m15_room.ui.main

import android.annotation.SuppressLint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m15_room.repository_SQL.Word
import com.example.m15_room.repository_SQL.WordDao
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val ONE = 1
private const val ERROR_STATE = "Некорректный ввод"
private const val CLEAR_TEXT = ""

class MainViewModel(
    private val wordDao: WordDao
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Error(ERROR_STATE))
    val state = _state.asStateFlow()

    private val allWords = this.wordDao.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )
    private val fiveWords = this.wordDao.getFive().shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L)
    )


    fun onChangeText(inputText: String) {
        if (inputText.contains(
                Regex("""^[A-Za-zа-яА-Я-]+$""")
            )
        ) _state.value = State.Ready
        else _state.value = State.Error(ERROR_STATE)
    }

    @SuppressLint("SuspiciousIndentation")
    fun getTextFromDB(): Flow<String> {
        return flow {
            var word = CLEAR_TEXT
            fiveWords.collect { words ->
                words.forEach { wordEach ->
                    word += "${wordEach.id} кол-во ${wordEach.amount}\n"
                }
                emit(word)
                word = CLEAR_TEXT
            }
        }
    }

    fun addOrUpdate(inputText: String) {
        viewModelScope.launch {
            allWords.collect { wordInBD ->
//                Log.e(TAG, "commitLaunch ${wordInBD.joinToString(" ")}")
                var check = 0
                wordInBD.forEach {
                    if (it.id == inputText) {
                        check = it.amount
                    }
                }
                if (check == 0) {
//                    Log.e(TAG, "$check - check, $inputText")
                    wordDao.insert(
                        Word
                            (
                            inputText,
                            ONE
                        )
                    )
                } else {
//                    Log.e(TAG, "update")
                    wordDao.update(
                        inputText,
                        check + ONE
                    )
                }
                cancel()
            }
        }
    }

    fun onClickDelete() {
        viewModelScope.launch {
            wordDao
                .delete()
        }
    }

    sealed class State {
        object Ready : State()
        data class Error(val message: String) : State()
    }
}
