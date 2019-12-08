package com.example.footy.ui.favourite_meals_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.footy.R
import com.example.footy.database.IngredientDatabase
import com.example.footy.databinding.FavouritesFragmentBinding
import com.example.footy.network.Meal
import com.example.footy.ui.list_of_meals_fragment.MealClickListener
import com.example.footy.ui.list_of_meals_fragment.MealsOfCategoryAdapter

class FavouritesFragment : Fragment() {


    lateinit var binding: FavouritesFragmentBinding
    companion object {
        fun newInstance() = FavouritesFragment()
    }

    private lateinit var viewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favourites_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = IngredientDatabase.getInstance(application).ingredientDatabaseDao
        val favouriteViewModelFactory = FavouritesViewModelFactory(dataSource, application)


        //Pass click listener to adapter and this callback will be called when item is clicked
        val adapter = MealsOfCategoryAdapter(MealClickListener { meal ->
            //when meal is clicked pass its corresponding ingredient to viewmodel
            viewModel.onRecipeClicked(meal.idMeal)

        })

        binding.recycler.adapter = adapter

        viewModel = ViewModelProviders.of(this, favouriteViewModelFactory)
            .get(FavouritesViewModel::class.java)

        viewModel.favoriteMeals.observe(this, Observer {

            if (it.isEmpty()) {
                return@Observer
            }

            val mealList = mutableListOf<Meal>()
            for ((index, value) in it.withIndex()) {
                mealList.add(Meal(it[index].idMeal, it[index].strMeal, it[index].strMealThumb))
                adapter.submitList(mealList)
            }

        })

        viewModel.navigateToOfflineRecipeFragment.observe(this, Observer {
            if (it != null) {
                // this.findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToRecipeFragment(it))
                val b = Bundle()
                b.putString("id", it)
                this.findNavController()
                    .navigate(R.id.action_favouritesFragment_to_offlineRecipeFragment, b)
                viewModel.navigationToRecipeFragmentComplete()
            }
        })




        binding.backImage.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}
