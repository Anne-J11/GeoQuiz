package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityAideBinding

private const val EXTRA_REPONSE_VRAIE = "com.example.geoquiz.reponse_vraie"
const val EXTRA_REPONSE_AFFICHE = "com.example.geoquiz.reponse_affiche"

class AideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAideBinding
    private var reponseCorrecte: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reponseCorrecte = intent.getBooleanExtra(EXTRA_REPONSE_VRAIE, false)

        binding.btnAfficheAide.setOnClickListener {
            val txtAfficher = when {
                reponseCorrecte -> R.string.btn_affiche_aide_vrai
                else -> R.string.btn_affiche_aide_faux
            }
            binding.twResponse.setText(txtAfficher)
            setResultReponseAffiche(true)
            finish()
        }
    }

    private fun setResultReponseAffiche(reponseAffiche: Boolean) {
        val donnees = Intent().apply {
            putExtra(EXTRA_REPONSE_AFFICHE, reponseAffiche)
        }
        setResult(Activity.RESULT_OK, donnees)
    }

    companion object {
        fun newIntent(packageContext: Context, reponseVrai: Boolean): Intent {
            return Intent(packageContext, AideActivity::class.java).apply {
                putExtra(EXTRA_REPONSE_VRAIE, reponseVrai)
            }
        }
    }
}