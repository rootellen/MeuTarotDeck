package br.edu.ifsp.scl.meutarotdeck.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.meutarotdeck.R
import br.edu.ifsp.scl.meutarotdeck.data.Constant.EXTRA_CARD_ID
import br.edu.ifsp.scl.meutarotdeck.data.TarotCard
import br.edu.ifsp.scl.meutarotdeck.databinding.FragmentDetailsBinding
import br.edu.ifsp.scl.meutarotdeck.databinding.FragmentTarotCardListBinding
import br.edu.ifsp.scl.meutarotdeck.viewmodel.TarotCardViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class DetailsFragment : Fragment() {
    lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    lateinit var viewModel: TarotCardViewModel
    lateinit var tarotCard: TarotCard
    var cardId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cardId = it.getInt(EXTRA_CARD_ID)
        }
        viewModel = ViewModelProvider(this).get(TarotCardViewModel::class.java)
        viewModel.getCardById(cardId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        viewModel.card.observe(viewLifecycleOwner) { result ->
            result?.let {
                tarotCard = result
                fragmentDetailsBinding.cardNameTv.text = tarotCard.cardName
                fragmentDetailsBinding.cardNumberTv.text = tarotCard.cardNumber.toString()
                fragmentDetailsBinding.cardElementTv.text = tarotCard.cardElement
            }
        }

        fragmentDetailsBinding.fab.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentView, TarotCardListFragment.newInstance())?.commit()
        }
        return fragmentDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.editCard -> {
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentView, AddCardFragment.newInstance(cardId))?.commit()
                        true
                    }
                    R.id.deleteCard ->{
                        viewModel.delete(tarotCard)

                        Snackbar.make(fragmentDetailsBinding.root, "Carta deletada", Snackbar.LENGTH_SHORT).show()
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentView, TarotCardListFragment.newInstance())?.commit()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_CARD_ID, id)
                }
            }
    }
}