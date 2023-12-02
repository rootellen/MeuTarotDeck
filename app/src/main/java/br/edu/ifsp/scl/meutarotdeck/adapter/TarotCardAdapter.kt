package br.edu.ifsp.scl.meutarotdeck.adapter

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.meutarotdeck.R
import br.edu.ifsp.scl.meutarotdeck.data.OnCardClickListener
import br.edu.ifsp.scl.meutarotdeck.data.TarotCard
import br.edu.ifsp.scl.meutarotdeck.databinding.FragmentDetailsBinding
import br.edu.ifsp.scl.meutarotdeck.databinding.TarotCardCellBinding

class TarotCardAdapter(private val onCardClickListener: OnCardClickListener):
    RecyclerView.Adapter<TarotCardAdapter.TarotCardViewHolder>(), Filterable {

    var cardList = ArrayList<TarotCard>()
    var cardListFilterable = ArrayList<TarotCard>()

    private lateinit var binding: TarotCardCellBinding

    inner class TarotCardViewHolder(view:TarotCardCellBinding) : RecyclerView.ViewHolder(view.root) {
        val cardName = view.nameTv
        val cardNumber = view.numberTv
        val cardElement = view.elementTv

        init {
            view.root.apply {
                setOnClickListener { onCardClickListener.onCardClick(adapterPosition) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarotCardViewHolder =
        TarotCardCellBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false).run {
            TarotCardViewHolder(this)
        }

    override fun getItemCount(): Int = cardListFilterable.size

    override fun onBindViewHolder(holder: TarotCardViewHolder, position: Int) {
        holder.cardName.text = cardListFilterable[position].cardName
        holder.cardNumber.text = cardListFilterable[position].cardNumber.toString()
        holder.cardElement.text = cardListFilterable[position].cardElement.toString()
    }

    override fun getFilter(): Filter {
       return object  : Filter() {
           override fun performFiltering(p: CharSequence?): FilterResults {
               if (p.toString().isEmpty()) {
                   cardListFilterable = cardList
               }
               else {
                   val resultList = ArrayList<TarotCard>()
                   for (row in cardList) {
                       if (row.cardName.lowercase().contains(p.toString().lowercase())) {
                           resultList.add(row)
                       }
                   }
                   cardListFilterable = resultList
               }
               val filterResult = FilterResults()
               filterResult.values = cardListFilterable
               return filterResult
           }

           override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
               cardListFilterable = p1?.values as ArrayList<TarotCard>
               notifyDataSetChanged()
           }
       }
    }

    fun updateList(list: List<TarotCard>) {
        cardList = list as ArrayList<TarotCard>
        cardListFilterable = cardList
        notifyDataSetChanged()
    }
}