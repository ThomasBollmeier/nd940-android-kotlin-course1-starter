package com.udacity.shoestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoelistBinding
import com.udacity.shoestore.databinding.ShoelistItemBinding
import com.udacity.shoestore.models.Shoe
import kotlin.time.Duration


/**
 * A simple [Fragment] subclass.
 * Use the [ShoeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeListFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var viewModel: ShoeListViewModel
    private lateinit var binding: FragmentShoelistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoelist, container, false)
        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)

        sharedViewModel.shoeList.observe(viewLifecycleOwner, Observer(::setShoeItems) )

        viewModel.itemClicked.observe(viewLifecycleOwner, Observer { evt ->
            if (evt != null) {
                val action = ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment(evt.index)
                findNavController().navigate(action)
                viewModel.onItemClickFinished()
            }
        })

        return binding.root
    }

    private fun setShoeItems(shoes: List<Shoe>) {
        binding.layoutShoelist.run {
            if (childCount > 0) {
                removeAllViews()
            }
            for ((idx, shoe) in shoes.withIndex()) {
                addShoeListItem(idx, shoe, this)
            }
        }
    }

    private fun addShoeListItem(shoeIdx: Int, shoe: Shoe, group: ViewGroup) {

        val bindingItem: ShoelistItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.shoelist_item, null, false)
        bindingItem.shoeItem = ShoeItem(shoeIdx, shoe)
        bindingItem.model = viewModel

        group.addView(bindingItem.root)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ShoeListFragment.
         */
        @JvmStatic
        fun newInstance() = ShoeListFragment()
    }
}