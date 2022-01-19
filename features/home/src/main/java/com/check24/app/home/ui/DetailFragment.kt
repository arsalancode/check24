package com.check24.app.home.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
//        setHasOptionsMenu(true)

        // This callback will only be called when MyFragment is at least Started.
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            vm.onClick.value = false
            findNavController().navigateUp()
        }

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

}