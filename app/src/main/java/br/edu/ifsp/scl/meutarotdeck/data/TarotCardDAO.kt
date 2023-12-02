package br.edu.ifsp.scl.meutarotdeck.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TarotCardDAO {
    @Insert
    suspend fun insert(tarotCard: TarotCard)

    @Update
    suspend fun update(tarotCard: TarotCard)

    @Delete
    suspend fun delete(tarotCard: TarotCard)

    @Query("SELECT * FROM tarotCard ORDER BY cardNumber")
    fun getAllCards(): LiveData<List<TarotCard>>

    @Query("SELECT * FROM tarotCard WHERE cardNumber=:id")
    fun getCardById(id: Int): LiveData<TarotCard>


}