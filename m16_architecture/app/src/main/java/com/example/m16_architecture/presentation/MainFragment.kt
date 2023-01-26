package com.example.m16_architecture.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.m16_architecture.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReload.setOnClickListener {
            viewModel.reloadUsefulActivity()
        }
        binding.btnHistory.setOnClickListener {
            binding.textHistory.text = viewModel.restoreListSaveSearch()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is MainViewModel.State.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    MainViewModel.State.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    MainViewModel.State.Success -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.stateIdea.collect {
                binding.textActivity.text = it
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.webLink.collect {
                binding.textVeb.text = it
            }
        }
    }

}