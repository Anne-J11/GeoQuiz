package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val INDEX_ACTUEL_KEY = "INDEX_ACTUEL_KEY"
const val EST_TRICHEUR_KEY = "EST_TRICHEUR_KEY"
const val NB_TRICHES_KEY = "NB_TRICHES_KEY"
private const val MAX_TRICHES = 3


class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val mStockQuestion = listOf(
        Question(R.string.question_paris, true),
        Question(R.string.question_ocean, true),
        Question(R.string.question_afrique, true),
        Question(R.string.question_amerique, false),
        Question(R.string.question_antarctique, false)
    )

    private var questionsTrichees: BooleanArray
        get() = savedStateHandle.get(EST_TRICHEUR_KEY) ?: BooleanArray(mStockQuestion.size)
        set(value) = savedStateHandle.set(EST_TRICHEUR_KEY, value)

    var nbTriches: Int
        get() = savedStateHandle.get(NB_TRICHES_KEY) ?: 0
        set(value) = savedStateHandle.set(NB_TRICHES_KEY, value)

    val tricheAutorisee: Boolean
        get() = nbTriches < MAX_TRICHES


    var mIndexActuel: Int
        get() = savedStateHandle.get(INDEX_ACTUEL_KEY) ?: 0
        set(value) = savedStateHandle.set(INDEX_ACTUEL_KEY, value)

    val estTricheurSurQuestionActuelle: Boolean
        get() = questionsTrichees[mIndexActuel]

    fun setQuestionActuelleTrichee() {
        val updatedArray = questionsTrichees
        if (!updatedArray[mIndexActuel]) {
            updatedArray[mIndexActuel] = true
            questionsTrichees = updatedArray
            nbTriches++
        }
    }


    val txtQuestionActuelle: Int
        get() = mStockQuestion[mIndexActuel].textResId

    val repQuestionActuelle: Boolean
        get() = mStockQuestion[mIndexActuel].reponse

    fun questionSuivante() {
        mIndexActuel = (mIndexActuel + 1) % mStockQuestion.size
    }

    fun questionPrecedente() {
        mIndexActuel = (mIndexActuel - 1 + mStockQuestion.size) % mStockQuestion.size
    }
}