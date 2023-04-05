package r42914lg.trykmm.android.ui.main.mainPage.address

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import r42914lg.trykmm.android.databinding.ItemListWithIconBinding
import r42914lg.trykmm.domain.models.local.AddressBsdListItem

class ListAddressAdapter(private val listener: (AddressBsdListItem) -> Unit) : RecyclerView.Adapter<ListAddressAdapter.ViewHolder>() {

    private val items = ArrayList<AddressBsdListItem>()

    fun setItems(items: List<AddressBsdListItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemListWithIconBinding = ItemListWithIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            listener(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val itemBinding: ItemListWithIconBinding,
        private val listener: (AddressBsdListItem) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: AddressBsdListItem) = with(itemBinding) {

            tvTitle.text = item.title
            tvDetails.text = item.details

            if (!item.selectedFlag) {
                itemBinding.tvTitle.setTextColor(Color.parseColor("#989898"))
                itemBinding.ivIcon.visibility = View.INVISIBLE
            } else {
                itemBinding.tvTitle.setTextColor(Color.parseColor("#FF272727"))
                itemBinding.ivIcon.visibility = View.VISIBLE
            }
        }
    }
}