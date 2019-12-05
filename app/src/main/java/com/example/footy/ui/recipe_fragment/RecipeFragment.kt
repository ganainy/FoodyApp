package com.example.footy.ui.recipe_fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.footy.Helper
import com.example.footy.R
import com.example.footy.databinding.RecipeFragmentBinding
import com.example.footy.network.Ingredient


class RecipeFragment : Fragment() {

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

        binding.fragment = this
        //Selected meal sent from CategoryFragment
        val selectedMeal = RecipeFragmentArgs.fromBundle(arguments!!).meal

        val recipeViewModelFactory = RecipeViewModelFactory(
            selectedMeal.idMeal.toInt(),
            requireNotNull(activity).application
        )
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

    }

    private fun showIngredients(ingredient: Ingredient) {
        //show ingredients in listview
        val ingredientsList = mutableListOf<String>()

        /*for( i:Int in 1..20 ){
            val strIngredient:String ="strIngredient".plus(i)
            val strMeasure:String ="strMeasure".plus(i)
            if(ingredient.strIngredient!="") ingredientsList.add("•"+ingredient.strIngredient +" : "+ ingredient.strMeasure)
        }*/

        if (ingredient.strIngredient1 != "") ingredientsList.add("•" + ingredient.strIngredient1 + " : " + ingredient.strMeasure1)
        if (ingredient.strIngredient2 != "") ingredientsList.add("•" + ingredient.strIngredient2 + " : " + ingredient.strMeasure2)
        if (ingredient.strIngredient3 != "") ingredientsList.add("•" + ingredient.strIngredient3 + " : " + ingredient.strMeasure3)
        if (ingredient.strIngredient4 != "") ingredientsList.add("•" + ingredient.strIngredient4 + " : " + ingredient.strMeasure4)
        if (ingredient.strIngredient5 != "") ingredientsList.add("•" + ingredient.strIngredient5 + " : " + ingredient.strMeasure5)
        if (ingredient.strIngredient6 != "") ingredientsList.add("•" + ingredient.strIngredient6 + " : " + ingredient.strMeasure6)
        if (ingredient.strIngredient7 != "") ingredientsList.add("•" + ingredient.strIngredient7 + " : " + ingredient.strMeasure7)
        if (ingredient.strIngredient8 != "") ingredientsList.add("•" + ingredient.strIngredient8 + " : " + ingredient.strMeasure8)
        if (ingredient.strIngredient9 != "") ingredientsList.add("•" + ingredient.strIngredient9 + " : " + ingredient.strMeasure9)
        if (ingredient.strIngredient10 != "") ingredientsList.add("•" + ingredient.strIngredient10 + " : " + ingredient.strMeasure10)
        if (ingredient.strIngredient11 != "") ingredientsList.add("•" + ingredient.strIngredient11 + " : " + ingredient.strMeasure11)
        if (ingredient.strIngredient12 != "") ingredientsList.add("•" + ingredient.strIngredient12 + " : " + ingredient.strMeasure12)
        if (ingredient.strIngredient13 != "") ingredientsList.add("•" + ingredient.strIngredient13 + " : " + ingredient.strMeasure13)
        if (ingredient.strIngredient14 != "") ingredientsList.add("•" + ingredient.strIngredient14 + " : " + ingredient.strMeasure14)
        if (ingredient.strIngredient15 != "") ingredientsList.add("•" + ingredient.strIngredient15 + " : " + ingredient.strMeasure15)
        if (ingredient.strIngredient16 != "") ingredientsList.add("•" + ingredient.strIngredient16 + " : " + ingredient.strMeasure16)
        if (ingredient.strIngredient17 != "") ingredientsList.add("•" + ingredient.strIngredient17 + " : " + ingredient.strMeasure17)
        if (ingredient.strIngredient18 != "") ingredientsList.add("•" + ingredient.strIngredient18 + " : " + ingredient.strMeasure18)
        if (ingredient.strIngredient19 != "") ingredientsList.add("•" + ingredient.strIngredient19 + " : " + ingredient.strMeasure19)
        if (ingredient.strIngredient20 != "") ingredientsList.add("•" + ingredient.strIngredient20 + " : " + ingredient.strMeasure20)

        val adapter = ArrayAdapter(
            requireNotNull(this.activity),
            R.layout.ingredient_item, ingredientsList
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
