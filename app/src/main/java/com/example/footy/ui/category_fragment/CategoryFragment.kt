package com.example.footy.ui.category_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.footy.R
import com.example.footy.databinding.CategoryFragmentBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: CategoryFragmentBinding

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel

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

        val selectedCategory = CategoryFragmentArgs.fromBundle(arguments!!).selectedCategory
        binding.category = selectedCategory

        val viewModelFactory =
            CategoryViewModelFactory(selectedCategory, requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)


        val adapter = MealsOfCategoryAdapter(MealClickListener { meal ->
            println(meal.strMeal)
        })

        binding.recycler.adapter = adapter

        viewModel.mealList.observe(this, Observer {
            adapter.submitList(it)
        })

    }

}
