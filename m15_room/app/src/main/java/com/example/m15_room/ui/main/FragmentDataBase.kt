package com.example.m15_room.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.m15_room.databinding.FragmentMainBinding
import com.example.m15_room.repository_SQL.App

class FragmentDataBase : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (requireContext().applicationContext as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.editText.addTextChangedListener { currentText ->
            viewModel.onChangeText(currentText.toString())
        }
        binding.btnAdd.setOnClickListener {
            val inputText = binding.editText.text.toString()
            viewModel.addOrUpdate(inputText)

        // Отчистка базы данных
        }
        binding.btnDelete.setOnClickListener {
            viewModel.onClickDelete()
        }
        // Обновление текста
        lifecycleScope.launchWhenStarted {
             viewModel.getTextFromDB().collect{
                 binding.message.text = it
             }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect{
                when(it){
                    is MainViewModel.State.Error -> {
                        binding.passwordLayout.error = it.message
                        binding.btnAdd.isEnabled = false
                    }
                    MainViewModel.State.Ready -> {
                        binding.passwordLayout.error = null
                        binding.btnAdd.isEnabled = true
                    }
                }
            }
        }
    }


}