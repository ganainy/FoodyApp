package com.example.footy.ui.list_of_meals_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.footy.R
import com.example.footy.databinding.CategoryFragmentBinding
import com.example.footy.network.Meal

class CategoryFragment : Fragment() {

    private lateinit var binding: CategoryFragmentBinding

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: MealsOfCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.category_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Selected category sent from HomeFragment
        val selectedCategory = CategoryFragmentArgs.fromBundle(arguments!!).selectedCategory
        binding.category = selectedCategory

        //Using factory to pass selected category with creation of ViewModel
        val viewModelFactory =
            CategoryViewModelFactory(selectedCategory, requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)


        //Pass click listener to adapter and this callback will be called when item is clicked
        adapter = MealsOfCategoryAdapter(MealClickListener { meal ->
            println(meal.strMeal)
            viewModel.onRecipeClicked(meal)
        })

        //navigate to recipe fragment when meal is clicked
        viewModel.navigateToSelectedRecipe.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(CategoryFragmentDirections.actionCategoryFragmentToRecipeFragment(it))
                viewModel.navigationToRecipeFragmentComplete()
            }
        })

        //set adapter to recycler
        binding.recycler.adapter = adapter

        //result meal list from API
        viewModel.mealList.observe(this, Observer {
            adapter.submitList(it)
            adapter.mealList = it as MutableList<Meal>

            println("CategoryFragment.onActivityCreated:observe called")
            setupSearchView()
            adapter.filter.filter(binding.searchView.query)

        })


        viewModel.mealLoadState.observe(this, Observer {
            binding.loadingState = it
        })

    }

    fun setupSearchView() { //do filtering when i type in search or click search
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryString: String): Boolean {
                adapter.filter.filter(queryString)
                return false
            }

            override fun onQueryTextChange(queryString: String): Boolean {
                adapter.filter.filter(queryString)
                return false
            }
        })
    }



}
