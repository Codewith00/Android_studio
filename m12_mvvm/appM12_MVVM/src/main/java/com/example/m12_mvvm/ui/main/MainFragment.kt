package com.example.m12_mvvm.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.m12_mvvm.databinding.FragmentMainBinding

private const val TAG = "MainFragment"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var containerLayout: LinearLayout
    private lateinit var scrollView: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentContext = requireActivity()
        containerLayout = binding.containerLayout
        scrollView = binding.scrollView
        for (j in 1..25) {
            containerLayout.addView(viewModel.addCardToScroll(currentContext))

        }

        binding.btnSearch.setOnClickListener {
            viewModel.onClick(binding.editText.text.toString())
        }

                    binding.editText.addTextChangedListener {
                            viewModel.onChangeListener(it.toString())
                        if(it!!.contains("\n")) {
                            viewModel.onClick(it.toString())
                            binding.editText.setText(it.toString().replace("\n", ""))
                        }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is State.NotReady -> {
                        Log.d(TAG, "NotReady")
                        binding.btnSearch.isEnabled = false
                        binding.loading.isVisible = false
                        binding.layoutText.error = it.message
                    }
                    State.Ready -> {
                        Log.d(TAG, "Ready")
                        binding.btnSearch.isEnabled = true
                        binding.layoutText.error = null
                    }
                    State.Loading -> {
                        Log.d(TAG, "Loading")
                        binding.btnSearch.isEnabled = false
                        binding.loading.isVisible = true
                        binding.editText.isEnabled = false
                    }
                    is State.SuccessNothing -> {
                        Log.d(TAG, "SuccessNothing")
                        binding.loading.isVisible = false
                        binding.editText.isEnabled = true
                        binding.btnSearch.isEnabled = true
                        val inputText = "По запросу <${it.message}> ничего не найдено"
                        binding.textView.text = inputText
                        binding.editText.setText(it.message)
                    }
                    is State.SuccessResult -> {
                        Log.d(TAG, "SuccessResult")
                        val inputText = "Нашлись результаты по запросу: ${it.message}"
                        binding.textView.text = inputText
                        binding.loading.isVisible = false
                        binding.editText.isEnabled = true
                        binding.btnSearch.isEnabled = true
                        binding.editText.setText(it.message)
                        scrollView.isFocusableInTouchMode
                        scrollView.scrollTo(0, it.position)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.inputText.collect{
                Toast.makeText(currentContext, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}



