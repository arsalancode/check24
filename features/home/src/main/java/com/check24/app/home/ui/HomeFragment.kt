package com.check24.app.home.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.check24.app.base.BaseFragment
import com.check24.app.home.HomeViewModel
import com.check24.app.home.R
import com.check24.app.home.databinding.HomeFragmentBinding
import com.check24.app.home.model.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.net.SocketTimeoutException

@FlowPreview
@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val TAG = "HomeFragment"

    @ExperimentalCoroutinesApi
    private val vm: HomeViewModel by activityViewModels() //viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = HomeFragmentBinding.inflate(inflater, container, false).apply {
        viewModel = vm
        lifecycleOwner = viewLifecycleOwner
        lfo = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerRepos.layoutManager = LinearLayoutManager(activity)

        vm.noConnectionEvent.observe(viewLifecycleOwner, {
            if (it.first) {
                noConnectionFragment(it.second is SocketTimeoutException)
                    .show(childFragmentManager, "")
            }
        })

        vm.onClick.observe(viewLifecycleOwner, {
            if (it){
                // Load next fragment
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                findNavController().navigate(action)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.all -> {
                Log.i(TAG, "ALL")
                vm.updateList(0)
                true
            }

            R.id.available -> {
                Log.i(TAG, "Available")
                vm.updateList(1)
                true
            }

            R.id.favorite -> {
                Log.i(TAG, "Favorite")
                vm.updateList(2)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}