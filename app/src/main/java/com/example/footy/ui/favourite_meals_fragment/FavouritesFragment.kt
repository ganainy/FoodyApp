package com.example.footy.ui.favourite_meals_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.footy.R
import com.example.footy.database.IngredientDatabase

class FavouritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    private lateinit var viewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = IngredientDatabase.getInstance(application).ingredientDatabaseDao

        val favouriteViewModelFactory = FavouritesViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, favouriteViewModelFactory)
            .get(FavouritesViewModel::class.java)

        viewModel.favoriteMeals.observe(this, Observer {

            println("FavouritesFragment.onActivityCreated:${it?.size}")

        })


    }

}
