package com.example.footy.ui.category_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footy.databinding.MealItemBinding
import com.example.footy.network.Meal


class MealsOfCategoryAdapter(private val clickListener: MealClickListener) :
    ListAdapter<Meal, MealsOfCategoryAdapter.ViewHolder>(DiffCallbackMeal()), Filterable {

    var mealList = mutableListOf<Meal>()
    lateinit var filteredList: MutableList<Meal>


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: MealClickListener, item: Meal) {
            binding.meal = item
            binding.clickListener = clickListener
            binding.executePendingBindings()


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredList = mutableListOf()
                if (charString.isEmpty()) {
                    filteredList = mealList

                } else {
                    for (meal in mealList) {
                        if (meal.strMeal.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(meal)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {

                submitList(filterResults.values as MutableList<Meal>?)

            }
        }
    }

}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class DiffCallbackMeal : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }
}

class MealClickListener(val clickListener: (meal: Meal) -> Unit) {
    fun onClick(meal: Meal) {
        return clickListener(meal)
    }
}