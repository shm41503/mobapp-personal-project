package edu.skku.map.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.skku.map.myapplication.databinding.ItemDrinkBinding

class DrinkAdapter(
    private val drinks: List<Drink>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    inner class DrinkViewHolder(private val binding: ItemDrinkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            binding.drinkNameTextView.text = drink.name
            binding.drinkDescriptionTextView.text = drink.description
            binding.drinkRatingTextView.text = "Rating: ${drink.rating}/5"
            val imageUrl = "file:///android_asset/${drink.imageUrl}"
            Glide.with(binding.drinkImageView.context).load(imageUrl).into(binding.drinkImageView)

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(drink)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(drinks[position])
    }

    override fun getItemCount(): Int = drinks.size

    interface OnItemClickListener {
        fun onItemClick(drink: Drink)
    }
}
