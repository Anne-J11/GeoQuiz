package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityAideBinding

private const val EXTRA_REPONSE_VRAIE = "com.example.geoquiz.reponse_vraie"
private const val EXTRA_NB_TRICHES = "com.example.geoquiz.nb_triches"

const val EXTRA_REPONSE_AFFICHE = "com.example.geoquiz.reponse_affiche"
private const val KEY_REPONSE_AFFICHEE = "reponse_affichee"

class AideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAideBinding
    private var reponseCorrecte: Boolean = false
    private var reponseAffichee = false
    private var nbTriches = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reponseCorrecte = intent.getBooleanExtra(EXTRA_REPONSE_VRAIE, false)
        reponseAffichee = savedInstanceState?.getBoolean(KEY_REPONSE_AFFICHEE, false) ?: false

        if (reponseAffichee) {
            afficherLaReponse()
            setResultReponseAffiche(true)
        }

        binding.btnAfficheAide.setOnClickListener {
            afficherLaReponse()
            reponseAffichee = true
            setResultReponseAffiche(true)
        }

        binding.btnRetour.setOnClickListener {
            finish()
        }

        nbTriches = intent.getIntExtra(EXTRA_NB_TRICHES, 0)
        binding.twNbTriches.text =
            getString(R.string.nb_triches, nbTriches)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_REPONSE_AFFICHEE, reponseAffichee)
    }

    private fun afficherLaReponse() {
        val txtAfficher = when {
            reponseCorrecte -> R.string.btn_affiche_aide_vrai
            else -> R.string.btn_affiche_aide_faux
        }
        binding.twResponse.setText(txtAfficher)
    }

    private fun setResultReponseAffiche(reponseAffiche: Boolean) {
        val donnees = Intent().apply {
            putExtra(EXTRA_REPONSE_AFFICHE, reponseAffiche)
        }
        setResult(Activity.RESULT_OK, donnees)
    }

    companion object {
        fun newIntent(
            packageContext: Context,
            reponseVrai: Boolean,
            nbTriches: Int
        ): Intent {
            return Intent(packageContext, AideActivity::class.java).apply {
                putExtra(EXTRA_REPONSE_VRAIE, reponseVrai)
                putExtra(EXTRA_NB_TRICHES, nbTriches)
            }
        }

    }
}