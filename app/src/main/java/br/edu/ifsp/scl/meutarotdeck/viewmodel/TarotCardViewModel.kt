package br.edu.ifsp.scl.meutarotdeck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.meutarotdeck.data.TarotCard
import br.edu.ifsp.scl.meutarotdeck.data.TarotCardDatabase
import br.edu.ifsp.scl.meutarotdeck.repository.TarotCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TarotCardViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TarotCardRepository
    lateinit var card: LiveData<TarotCard>
    var allCards : LiveData<List<TarotCard>>
    var cardId: Int? = null

    init {
        val dao = TarotCardDatabase.getDatabase(application).tarotCardDAO()
        repository = TarotCardRepository(dao)
        allCards = repository.getAllCards()
    }

    fun insert(tarotCard: TarotCard) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(tarotCard)
    }

    fun delete(tarotCard: TarotCard) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(tarotCard)
    }

    fun getCardById(id: Int) {
        viewModelScope.launch {
            card = repository.getCardById(id)
        }
    }

    fun update(tarotCard: TarotCard) = viewModelScope.launch(Dispatchers.IO){
        repository.update(tarotCard)
    }
}