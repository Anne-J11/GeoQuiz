package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val INDEX_ACTUEL_KEY = "INDEX_ACTUEL_KEY"
const val EST_TRICHEUR_KEY = "EST_TRICHEUR_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val mStockQuestion = listOf(
        Question(R.string.question_paris, true),
        Question(R.string.question_ocean, true),
        Question(R.string.question_afrique, true),
        Question(R.string.question_amerique, false),
        Question(R.string.question_antarctique, false)
    )

    // Propriété pour suivre si l'utilisateur a triché
    var estTricheur: Boolean
        get() = savedStateHandle.get(EST_TRICHEUR_KEY) ?: false
        set(value) = savedStateHandle.set(EST_TRICHEUR_KEY, value)

    // Propriété calculée avec getter et setter
    var mIndexActuel: Int
        get() = savedStateHandle.get(INDEX_ACTUEL_KEY) ?: 0
        set(value) = savedStateHandle.set(INDEX_ACTUEL_KEY, value)

    // Propriété pour obtenir le texte de la question actuelle
    val txtQuestionActuelle: Int
        get() = mStockQuestion[mIndexActuel].textResId

    // Propriété pour obtenir la réponse de la question actuelle
    val repQuestionActuelle: Boolean
        get() = mStockQuestion[mIndexActuel].reponse

    // Fonction pour passer à la question suivante
    fun questionSuivante() {
        mIndexActuel = (mIndexActuel + 1) % mStockQuestion.size
    }

    // Fonction pour passer à la question précédente
    fun questionPrecedente() {
        mIndexActuel = (mIndexActuel - 1 + mStockQuestion.size) % mStockQuestion.size
    }
}