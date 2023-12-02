package br.edu.ifsp.scl.meutarotdeck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.meutarotdeck.databinding.ActivityMainBinding
import br.edu.ifsp.scl.meutarotdeck.ui.TarotCardListFragment

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.toolbar.toolbarApp)
        supportActionBar?.title = getString(R.string.app_name)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentView, TarotCardListFragment.newInstance()).commit()
    }
}