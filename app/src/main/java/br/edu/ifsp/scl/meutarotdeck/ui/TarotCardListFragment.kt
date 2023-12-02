package br.edu.ifsp.scl.meutarotdeck.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.meutarotdeck.MainActivity
import br.edu.ifsp.scl.meutarotdeck.R
import br.edu.ifsp.scl.meutarotdeck.adapter.TarotCardAdapter
import br.edu.ifsp.scl.meutarotdeck.data.OnCardClickListener
import br.edu.ifsp.scl.meutarotdeck.databinding.FragmentTarotCardListBinding
import br.edu.ifsp.scl.meutarotdeck.viewmodel.TarotCardViewModel


class TarotCardListFragment : OnCardClickListener, Fragment() {

    private lateinit var fragmentTarotCardListBinding: FragmentTarotCardListBinding

    lateinit var viewModel: TarotCardViewModel
    private val tarotCardAdapter: TarotCardAdapter by lazy {
        TarotCardAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.subtitle = "Lista de Cartas"
        fragmentTarotCardListBinding = FragmentTarotCardListBinding.inflate(inflater, container, false)
        fragmentTarotCardListBinding.apply {
            fab.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentView, AddCardFragment.newInstance(null))?.commit()
            }
        }
        return fragmentTarotCardListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
                searchIcon.setColorFilter(Color.WHITE)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        tarotCardAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TarotCardListFragment()
    }

    private fun configureRecyclerView() {
        viewModel = ViewModelProvider(this).get(TarotCardViewModel::class.java)

        viewModel.allCards.observe(viewLifecycleOwner) { list ->
            list?.let {
                tarotCardAdapter.updateList(list)
            }
        }

        fragmentTarotCardListBinding.cardsRv.layoutManager = LinearLayoutManager(requireContext())
        fragmentTarotCardListBinding.cardsRv.adapter = tarotCardAdapter
    }

    override fun onCardClick(position: Int) {
        val c = tarotCardAdapter.cardListFilterable[position]
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentView, DetailsFragment.newInstance(c.cardNumber))?.commit()
    }
}