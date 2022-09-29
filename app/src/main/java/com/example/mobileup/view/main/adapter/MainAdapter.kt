package com.example.mobileup.view.main.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileup.R
import com.example.mobileup.databinding.CoinItemBinding
import com.example.mobileup.models.Coin
import com.google.android.material.chip.Chip
import com.google.gson.JsonArray
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.roundToInt

class MainAdapter :  RecyclerView.Adapter<MainAdapter.DataViewHolder>() {



    private var listCoins = ArrayList<Coin>()
    private lateinit var data : JsonArray

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val binding = CoinItemBinding.bind(itemView)
        private lateinit var coin : Coin
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(coin: Coin) = with(binding) {
            titleCoin.text = coin.name
            subTitleCoin.text = coin.symbol

            this@DataViewHolder.coin = coin

            val price = String.format(Locale.US, "%1$,.2f", coin.price)

            val activity = itemView.context as AppCompatActivity
            if (activity.findViewById<Chip>(R.id.usdChip).isChecked) {
                priceCoin.text = "$ $price"
            } else priceCoin.text = "€ $price"

            if (coin.priceChange > 0) {
                priceChangeCoin.text = "+" +
                        (( coin.priceChange * 100.0 ).roundToInt() / 100.0).toString() + "%"
                priceChangeCoin.setTextColor(Color.parseColor("#2A9D8F"))
            } else {
                priceChangeCoin.text = (( coin.priceChange * 100.0 ).roundToInt()
                        / 100.0).toString() + "%"
                priceChangeCoin.setTextColor(Color.parseColor("#EB5757"))
            }


            Picasso.get().load(coin.logo).into(iconCoin)

            root.setOnClickListener(this@DataViewHolder)
        }

        override fun onClick(v : View?) {
            val bundle = bundleOf("id" to coin.id, "logo" to coin.logo, "title" to coin.name)

            Navigation.findNavController(v!!)
                .navigate(R.id.action_listFragment_to_aboutFragment, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_item, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(listCoins[position])
    }

    override fun getItemCount() = listCoins.size

    @SuppressLint("NotifyDataSetChanged")
    fun addCoins(coins: JsonArray) {
        listCoins.clear()
        this.data = coins
        for (i in 0 until data.size()) {
            listCoins.add(Coin(
                data.get(i).asJsonObject.get("id").asString,
                data.get(i).asJsonObject.get("symbol").asString,
                data.get(i).asJsonObject.get("name").asString,
                data.get(i).asJsonObject.get("image").asString,
                data.get(i).asJsonObject.get("current_price").asDouble,
                data.get(i).asJsonObject.get("price_change_percentage_24h").asDouble
            ))
        }
        notifyDataSetChanged()
    }
}