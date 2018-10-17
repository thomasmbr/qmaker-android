package com.qmakercorp.qmaker.ui.main.fragments.quizzes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmakercorp.qmaker.R
import com.qmakercorp.qmaker.adapters.AnswersAdapter
import com.qmakercorp.qmaker.components.OnStartDragListener
import com.qmakercorp.qmaker.components.SimpleItemTouchHelperCallback
import com.qmakercorp.qmaker.data.model.Answer
import com.qmakercorp.qmaker.data.model.Question
import com.qmakercorp.qmaker.data.model.Quiz
import kotlinx.android.synthetic.main.fragment_question.*

class QuestionFragment : Fragment(), OnStartDragListener {

    private lateinit var adapter: AnswersAdapter
    private lateinit var quiz: Quiz
    private lateinit var question: Question
    private var mItemTouchHelper: ItemTouchHelper? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { it ->
            quiz = it.getParcelable("quiz") as Quiz
            question = it.getParcelable("question") as Question
        }
        initializeViews()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        viewHolder?.let { mItemTouchHelper?.startDrag(it) }
    }

    private fun initializeViews() {
        context?.let {
            val answers = initializeAnswers()
            adapter = AnswersAdapter(it, answers, this)
            rv_answers.adapter = adapter
            rv_answers.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val callback = SimpleItemTouchHelperCallback(adapter)
            mItemTouchHelper = ItemTouchHelper(callback)
            mItemTouchHelper?.attachToRecyclerView(rv_answers)
        }
    }

    private fun initializeAnswers(): MutableList<Answer> {
        val answers = mutableListOf<Answer>()
        question.answers.forEachIndexed { index, description ->
            answers.add(Answer(index, description, false))
        }
        question.trueAnswers.forEach {
            answers[it].isTrue = true
        }
        return answers
    }


}
