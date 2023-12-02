package br.edu.ifsp.scl.meutarotdeck.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.meutarotdeck.data.TarotCard
import br.edu.ifsp.scl.meutarotdeck.data.TarotCardDAO

class TarotCardRepository(private val tarotCardDAO: TarotCardDAO) {

    suspend fun insert(tarotCard: TarotCard) {
        tarotCardDAO.insert(tarotCard)
    }

    suspend fun delete(tarotCard: TarotCard) {
        tarotCardDAO.delete(tarotCard)
    }

    suspend fun update(tarotCard: TarotCard) {
        tarotCardDAO.update(tarotCard)
    }

    fun getAllCards(): LiveData<List<TarotCard>> {
        return tarotCardDAO.getAllCards()
    }

    fun getCardById(id: Int): LiveData<TarotCard> {
        return tarotCardDAO.getCardById(id)
    }

}