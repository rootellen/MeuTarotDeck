package br.edu.ifsp.scl.meutarotdeck.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.scl.meutarotdeck.MainActivity
import br.edu.ifsp.scl.meutarotdeck.R
import br.edu.ifsp.scl.meutarotdeck.data.Constant.EXTRA_CARD_ID
import br.edu.ifsp.scl.meutarotdeck.data.TarotCard
import br.edu.ifsp.scl.meutarotdeck.databinding.FragmentAddCardBinding
import br.edu.ifsp.scl.meutarotdeck.viewmodel.TarotCardViewModel
import com.google.android.material.snackbar.Snackbar


class AddCardFragment : Fragment() {

    lateinit var fragmentAddCardBinding: FragmentAddCardBinding
    lateinit var viewModel: TarotCardViewModel
    var cardId: Int? = null
    private lateinit var tarotCard: TarotCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TarotCardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddCardBinding = FragmentAddCardBinding.inflate(inflater, container, false)
        fragmentAddCardBinding.apply {
            if (cardId != null) {
                (activity as MainActivity).supportActionBar?.subtitle = "Editar Carta de Tarot"
                viewModel.getCardById(cardId!!)
                viewModel.card.observe(viewLifecycleOwner) { result ->
                    result?.let {
                        tarotCard = result
                        this.cardNameEt.setText(tarotCard.cardName)
                        this.cardNumberSp.setSelection(tarotCard.cardNumber)
                        this.cardElementSp.setSelection(getIndex(this.cardElementSp, tarotCard.cardElement))
                    }
                }
                saveBt.setOnClickListener {
                    tarotCard.cardName = this.cardNameEt.text.toString()
                    tarotCard.cardNumber = this.cardNumberSp.selectedItem.toString().toInt()
                    tarotCard.cardElement = this.cardElementSp.selectedItem.toString()

                    if (!tarotCard.cardName.isBlank()) {
                        viewModel.update(tarotCard)
                        Snackbar.make(
                            fragmentAddCardBinding.root,
                            "Carta editada",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragmentView, TarotCardListFragment.newInstance())
                            ?.commit()
                    } else {
                        Snackbar.make(
                            fragmentAddCardBinding.root,
                            "Carta não editada, campo nome em branco",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                (activity as MainActivity).supportActionBar?.subtitle = "Nova Carta de Tarot"
                saveBt.setOnClickListener {

                    var card = TarotCard(
                        0,
                        this.cardNumberSp.selectedItem.toString().toInt(),
                        this.cardNameEt.text.toString(),
                        this.cardElementSp.selectedItem.toString()
                    )

                    if (!card.cardName.isBlank()){
                        viewModel.insert(card)
                        Snackbar.make(fragmentAddCardBinding.root, "Carta inserida", Snackbar.LENGTH_SHORT).show()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragmentView, TarotCardListFragment.newInstance())
                            ?.commit()
                    } else {
                        Snackbar.make(fragmentAddCardBinding.root, "Carta não inserida, campo nome em branco", Snackbar.LENGTH_SHORT).show()
                    }

                }
            }
        }
        return fragmentAddCardBinding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int?) = AddCardFragment().apply {
            this.cardId = id
        }
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }
}