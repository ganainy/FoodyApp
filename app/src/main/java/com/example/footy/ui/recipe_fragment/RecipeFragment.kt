package com.example.footy.ui.recipe_fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.footy.R
import com.example.footy.databinding.RecipeFragmentBinding


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

}
