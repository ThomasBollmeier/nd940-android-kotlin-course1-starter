package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ShoelistItemBinding
import com.udacity.shoestore.models.Shoe


/**
 * A simple [Fragment] subclass.
 * Use the [ShoeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoeListFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var viewModel: ShoeListViewModel
    private lateinit var itemViewModel: ShoeListItemViewModel
    private lateinit var binding: FragmentShoeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)

        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)
        binding.model = viewModel

        itemViewModel = ViewModelProvider(this).get(ShoeListItemViewModel::class.java)

        sharedViewModel.shoeList.observe(viewLifecycleOwner, Observer(::setShoeItems))

        sharedViewModel.loggedOut.observe(viewLifecycleOwner, Observer { loggedOut ->
            if (!loggedOut) {
                return@Observer
            }
            val action =
                ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment()
            findNavController().navigate(action)
            sharedViewModel.logoutFinished()
        })

        viewModel.addClicked.observe(viewLifecycleOwner, Observer { clicked ->
            if (!clicked) {
                return@Observer
            }
            val action =
                ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment(-1)
            findNavController().navigate(action)
            viewModel.onAddShoeClickFinshed()
        })

        itemViewModel.itemClicked.observe(viewLifecycleOwner, Observer { evt ->
            if (evt == null) {
                return@Observer
            }
            val action =
                ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment(evt.index)
            findNavController().navigate(action)
            itemViewModel.onItemClickFinished()
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_shoe_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.item_logout) {
            sharedViewModel.logout()
            true
        } else {
            false
        }
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
            layoutInflater, R.layout.shoelist_item, null, false
        )
        bindingItem.shoeItem = ShoeItem(shoeIdx, shoe)
        bindingItem.model = itemViewModel

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