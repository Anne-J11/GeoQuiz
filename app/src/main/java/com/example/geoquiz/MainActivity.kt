package com.example.geoquiz

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Utilisation de la délégation de propriété pour le ViewModel
    private val quizViewModel: QuizViewModel by viewModels()

    private val demarreTriche = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.estTricheur =
                result.data?.getBooleanExtra(EXTRA_REPONSE_AFFICHE, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) appelée")

        // Initialiser View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Log pour voir l'instance du ViewModel
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // Écouteur bouton Vrai
        binding.boutonVrai.setOnClickListener {
            verifieReponse(true)
        }

        // Écouteur bouton Faux
        binding.boutonFaux.setOnClickListener {
            verifieReponse(false)
        }

        // Écouteur pour le bouton Suivant
        binding.btnSuivant.setOnClickListener {
            quizViewModel.questionSuivante() // Correction: appel sur l'instance
            majQuestion()
        }

        // Écouteur pour le bouton Précédent
        binding.btnPrecedent.setOnClickListener {
            quizViewModel.questionPrecedente()
            majQuestion()
        }

        // Écouteur pour le bouton Triche
        binding.btnTriche?.setOnClickListener { // Correction: ID du bouton
            val reponseCorrecte = quizViewModel.repQuestionActuelle
            val intention = AideActivity.newIntent(this@MainActivity, reponseCorrecte)
            demarreTriche.launch(intention)
        }
        majQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() appelée")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() appelée")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() appelée")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() appelée")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() appelée")
    }

    /**
     * Met à jour le texte de la question affichée
     */
    private fun majQuestion() {
        val questionResId = quizViewModel.txtQuestionActuelle
        binding.twQuestion.setText(questionResId)
    }

    /**
     * Vérifie la réponse de l'utilisateur et affiche un toast
     */
    private fun verifieReponse(repUser: Boolean) {
        val messageId = when {
            quizViewModel.estTricheur -> R.string.toast_triche
            repUser == quizViewModel.repQuestionActuelle -> R.string.toast_correct
            else -> R.string.toast_incorrect
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
    }
}
