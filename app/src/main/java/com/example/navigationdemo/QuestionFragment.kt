package com.example.navigationdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.navigationdemo.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    lateinit var binding: FragmentQuestionBinding
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    var questionIndex = 0
    var score: Int = 0

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "Which Order have been implemented by Malaysia government to prevent COVID-19?",
            answers = listOf(
                "Movement Control Order",
                "Multiple Choice Order",
                "More Coffee Order "
            )
        ),
        Question(
            text = "What is FMCO?",
            answers = listOf(
                "Full Movement Control Oder",
                "Fun Movement Control Oder",
                "Forever Movement Control Oder"
            )
        )
    )

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()

        binding.tvQuestion.text = currentQuestion.text
        binding.opA.text = answers[0]
        binding.opB.text = answers[1]
        binding.opC.text = answers[2]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question, container, false)

        setQuestion()

        binding.btnNext.setOnClickListener() {
            val checkedId = binding.radioGroup.checkedRadioButtonId

            if (checkedId != -1) {
                var answerIndex = 0

                when (checkedId) {
                    R.id.opA -> answerIndex = 0
                    R.id.opB -> answerIndex = 1
                    R.id.opC -> answerIndex = 2
                }
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    score += 1
                }

                if (questionIndex < 1) {
                    questionIndex += 1
                    binding.radioGroup.clearCheck()
                    setQuestion()

                } else {
                    // todo:: navigate to thankyou fragment
                    var percentage = (score.toFloat() / questions.size.toFloat()) * 100
                        val action = QuestionFragmentDirections.actionQuestionFragmentToThankYouFragment(percentage)
                    Navigation.findNavController(it).navigate(action)

                }
            } else {
                Toast.makeText(context, "please select answer", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }
}