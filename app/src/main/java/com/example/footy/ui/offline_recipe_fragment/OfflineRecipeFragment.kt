package com.example.footy.ui.offline_recipe_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.footy.R
import com.example.footy.database.IngredientDatabase
import com.example.footy.databinding.OfflineRecipeFragmentBinding
import com.example.footy.network.Ingredient
import com.example.footy.utils.Helper
import com.example.footy.utils.filterIngredientsList


class OfflineRecipeFragment : Fragment() {

    lateinit var binding: OfflineRecipeFragmentBinding

    companion object {
        fun newInstance() = OfflineRecipeFragment()
    }

    private lateinit var viewModel: OfflineRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.offline_recipe_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(activity).application
        val dataSource = IngredientDatabase.getInstance(application).ingredientDatabaseDao

        binding.fragment = this

        val mealId = arguments?.getString("id")

        val viewModelFactory = OfflineRecipeViewModelFactory(dataSource, mealId!!)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(OfflineRecipeViewModel::class.java)



        viewModel.ingredient.observe(this, Observer {
            binding.ingredient = it
            showIngredients(it)
        })

        viewModel.deleteDone.observe(this, Observer {
            if (it != null) {
                binding.deleteImage.visibility = View.GONE
                Toast.makeText(context, "Recipe deleted from your favourites", Toast.LENGTH_LONG).show()
                println("OfflineRecipeFragment.onActivityCreated:${view?.findNavController()?.currentDestination?.label}")
                this.findNavController()
                    .navigate(R.id.action_offlineRecipeFragment_to_favouritesFragment)
                viewModel.navigationComplete()
            }
        })

    }


    private fun showIngredients(ingredient: Ingredient) {
        //show ingredients in listview


        val adapter = ArrayAdapter(
            requireNotNull(this.activity),
            R.layout.ingredient_item,
            filterIngredientsList(ingredient) //removes ingredients that come from backend as empty string
        )

        binding.ingredientsListView.adapter = adapter

        //helper class to fix listview height issue
        Helper.getListViewSize(binding.ingredientsListView)
    }


    fun navigateUp(): Unit {
        activity?.onBackPressed()
    }

    fun confirmDelete(): Unit {

        AlertDialog.Builder(activity)
            .setMessage("Are you sure you want to delete recipe from favourites?")
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                viewModel.deleteFromDB()
            }
            .setNegativeButton("No", null)
            .setIcon(R.drawable.ic_delete_forever_black_24dp)
            .show()
    }

}
