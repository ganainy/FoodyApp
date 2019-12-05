package com.example.footy.ui.list_of_categories_fragment

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
import com.example.footy.databinding.HomeFragmentBinding
import com.example.footy.ui.list_of_categories_fragment.categories_adapter.CategoryClickListener
import com.example.footy.ui.list_of_categories_fragment.categories_adapter.MealCategoriesAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val adapter = MealCategoriesAdapter(CategoryClickListener { category ->
            viewModel.onCategoryClicked(category)
        })

        binding.recycler.adapter = adapter


        viewModel.categories.observe(this, Observer {
            it?.let {
                adapter.submitList(it.categories)
            }
        })

        viewModel.categoriesLoadState.observe(this, Observer {
            binding.loadingState = it
        })


        viewModel.navigateToSelectedCategory.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToCategoryFragment(it))
                viewModel.navigationToCategoryFragmentComplete()
            }
        })
    }

}


//homeTextView.setOnClickListener { view -> view.findNavController().navigate(R.id.action_homeFragment_to_recipeFragment) }