package com.example.m12_mvvm.ui.main


import com.example.m12_mvvm.textStorage.News
import kotlinx.coroutines.delay

class MainRepository {

   private val contentRepository = News()

    suspend fun getData(text: String): String? {
        delay(2500L)
            contentRepository.textsContainer.forEach { containText ->
                if (text.lowercase() == containText.lowercase())
                    return text
        }
            contentRepository.textsLogo.forEach{ logoText ->
                if (text.lowercase() == logoText.lowercase())
                    return text
            }
        return null
    }

}