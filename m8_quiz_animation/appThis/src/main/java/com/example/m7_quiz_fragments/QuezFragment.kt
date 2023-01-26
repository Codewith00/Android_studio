package com.example.m7_quiz_fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.forEachIndexed
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.My_first_project.quiz.QuizStorage
import com.example.m7_quiz_fragments.databinding.FragmentQuezBinding
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QuezFragment : Fragment() {
    fun checkIndexRadioButtonInGroup(group: RadioGroup): Int {
        group.forEachIndexed { index, button -> if (group.checkedRadioButtonId == button.id) return index }

        return group.indexOfChild(group.focusedChild)
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentQuezBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuezBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val quiz = QuizStorage.getQuiz(QuizStorage.Locale.Ru)
        val checkAnswers = mutableListOf(-1, -1, -1)
        alpha(binding.textViewRadioGroup1, 600)
        alpha(binding.radioGroup1, 900)
        alpha(binding.textViewRadioGroup2, 1400)
        alpha(binding.radioGroup2, 1700)
        alpha(binding.textViewRadioGroup3, 1900)
        alpha(binding.radioGroup3, 2100)

        alpha(binding.btnBack, 5000)
        alpha(binding.btnPop, 5000)
        alpha(binding.btnStart, 5000)


        binding.textViewRadioGroup1.text = quiz.questions[0].question
        binding.textViewRadioGroup2.text = quiz.questions[1].question
        binding.textViewRadioGroup3.text = quiz.questions[2].question

        binding.radioButton1.text = quiz.questions[0].answers[0]
        binding.radioButton2.text = quiz.questions[0].answers[1]
        binding.radioButton3.text = quiz.questions[0].answers[2]
        binding.radioButton4.text = quiz.questions[0].answers[3]

        binding.radioButton21.text = quiz.questions[1].answers[0]
        binding.radioButton22.text = quiz.questions[1].answers[1]
        binding.radioButton23.text = quiz.questions[1].answers[2]
        binding.radioButton24.text = quiz.questions[1].answers[3]

        binding.radioButton31.text = quiz.questions[2].answers[0]
        binding.radioButton32.text = quiz.questions[2].answers[1]
        binding.radioButton33.text = quiz.questions[2].answers[2]
        binding.radioButton34.text = quiz.questions[2].answers[3]

        binding.btnPop.setOnClickListener {
            binding.radioGroup1.clearCheck()
            binding.radioGroup2.clearCheck()
            binding.radioGroup3.clearCheck()

        }
        binding.btnBack.setOnClickListener {
            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_in_right,
                    R.anim.slide_out_rigth
                )
                replace<WelcomeFragment>(R.id.nav_host_fragment_container)
            }
        }
        binding.btnStart.setOnClickListener {

            checkAnswers[0] = checkIndexRadioButtonInGroup(binding.radioGroup1)
            checkAnswers[1] = checkIndexRadioButtonInGroup(binding.radioGroup2)
            checkAnswers[2] = checkIndexRadioButtonInGroup(binding.radioGroup3)
            if (checkAnswers[0] == -1 ||
                checkAnswers[1] == -1 ||
                checkAnswers[2] == -1
            ) {
                Snackbar.make(it, "Выбраны не все варианты ответов", Snackbar.LENGTH_SHORT).show()
            } else {
                println(checkAnswers)
                val answersString = QuizStorage.answer(quiz, checkAnswers)
                val bundle = Bundle().apply {
                    putString("param1", answersString)
                }
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_right,
                        R.anim.slide_out_rigth
                    )
                    replace<ResultFragment>(
                        containerViewId = R.id.nav_host_fragment_container,
                        args = bundle
                    )
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuezFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun alpha(item: View, customDuration: Long){
        ObjectAnimator.ofFloat(item, View.ALPHA, 0f, 1f).apply {
            duration = customDuration
            start()
        }
    }
}