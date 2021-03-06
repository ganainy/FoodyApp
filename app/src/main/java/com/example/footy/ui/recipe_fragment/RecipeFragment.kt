package com.example.footy.ui.recipe_fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import com.example.footy.R
import com.example.footy.database.IngredientDatabase
import com.example.footy.databinding.RecipeFragmentBinding
import com.example.footy.network.Ingredient
import com.example.footy.utils.ConnectionBroadcastReceiver
import com.example.footy.utils.Helper
import com.example.footy.utils.filterIngredientsList


class RecipeFragment : Fragment() {

    private lateinit var recipeViewModelFactory: RecipeViewModelFactory
    lateinit var binding: RecipeFragmentBinding

    companion object {
        fun newInstance() = RecipeFragment()
    }

    private lateinit var viewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(activity).application
        val dataSource = IngredientDatabase.getInstance(application).ingredientDatabaseDao


        binding.fragment = this
        //Selected meal Id depending on the fragment that opened recipe fragment

        val selectedMeal = RecipeFragmentArgs.fromBundle(arguments!!).meal
        val mealId = selectedMeal.idMeal.toInt()
        recipeViewModelFactory = RecipeViewModelFactory(mealId, application, dataSource)



        viewModel =
            ViewModelProviders.of(this, recipeViewModelFactory).get(RecipeViewModel::class.java)

        binding.viewModel = viewModel


        //This is called when viewModel loads recipe
        viewModel.recipe.observe(this, Observer {
            binding.recipe = it
            showIngredients(it.meals[0])

        })


        //this is called when play on youtube button is clicked
        viewModel.ytLink.observe(this, Observer {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(it)
            )
            try {
                context!!.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                context!!.startActivity(webIntent)
                println("RecipeFragment.onActivityCreated:${ex.message}")
            }
        })

        //set favorite image depending on is meal in favorites or not
        viewModel.isFavorite.observe(this, Observer {
            if (it) {
                //show animation
                binding.favoriteAnimation.speed = 1f
                binding.favoriteAnimation.playAnimation()
            } else {
                //show animation in reverse
                binding.favoriteAnimation.speed = -1f
                binding.favoriteAnimation.playAnimation()
            }
        })


        //this state used to show loading or internt error in layout
        viewModel.recipeLoadState.observe(this, Observer {
            binding.loadState = it
        })

        //show toast on adding or deleting to favourites
        viewModel.toast.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        ConnectionBroadcastReceiver.registerToFragmentAndAutoUnregister(
            activity!!,
            this,
            object : ConnectionBroadcastReceiver() {
                override fun onConnectionChanged(hasConnection: Boolean) {
                    println("FavouritesFragment.onConnectionChanged:$hasConnection")
                    if (hasConnection) {
                        viewModel.getSpecificRecipe(mealId)
                    }
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
        println("RecipeFragment.navigateUp:")

        activity?.onBackPressed()

    }


}
