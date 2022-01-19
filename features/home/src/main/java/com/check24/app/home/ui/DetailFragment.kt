package com.check24.app.home.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.check24.app.base.BaseFragment
import com.check24.app.home.HomeViewModel
import com.check24.app.home.R
import com.check24.app.home.databinding.DetailFragmentBinding
import com.check24.app.home.model.UiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.net.SocketTimeoutException

@FlowPreview
@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    private val TAG = "DetailFragment"

    @ExperimentalCoroutinesApi
    private val vm: HomeViewModel by activityViewModels() //viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DetailFragmentBinding.inflate(inflater, container, false).apply {
        viewModel = vm
        lifecycleOwner = viewLifecycleOwner
        lfo = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.noConnectionEvent.observe(viewLifecycleOwner, {
            if (it.first) {
                noConnectionFragment(it.second is SocketTimeoutException)
                    .show(childFragmentManager, "")
            }
        })

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search_menu, menu)
//        val search = menu.findItem(R.id.search)
//        val searchView: SearchView = MenuItemCompat.getActionView(search) as SearchView
//        setupSearchView(searchView)
//    }



}