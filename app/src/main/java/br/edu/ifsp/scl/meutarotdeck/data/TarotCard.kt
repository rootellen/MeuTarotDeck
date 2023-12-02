package br.edu.ifsp.scl.meutarotdeck.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TarotCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var cardNumber: Int,
    var cardName: String,
    var cardElement: String
) : Parcelable
