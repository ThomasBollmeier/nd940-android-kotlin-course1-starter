package com.udacity.shoestore.ui

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding

class ShoeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ShoeDetailFragment()
    }

    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var viewModel: ShoeDetailViewModel
    private lateinit var binding: FragmentShoeDetailBinding
    private val args: ShoeDetailFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        val viewModelFactory = ShoeDetailViewModelFactory(sharedViewModel, args.shoeIdx)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShoeDetailViewModel::class.java)
        binding.model = viewModel

        viewModel.canceled.observe(viewLifecycleOwner, Observer { canceled ->
            if (canceled) {
                findNavController().navigateUp()
                viewModel.onCanceledFinished()
            }
        })

        viewModel.saved.observe(viewLifecycleOwner, Observer { saved ->
            if (saved) {
                findNavController().navigateUp()
                viewModel.onSavedFinished()
            }
        })

        return binding.root
    }

}