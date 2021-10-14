package com.kalevet.pixagetapp.ui.imageList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalevet.pixagetapp.R
import com.kalevet.pixagetapp.data.models.image.ImageItemComparator
import com.kalevet.pixagetapp.ui.base.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noResults: TextView
    private val viewModel: ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_image_list, container, false)
        initRecyclerView(rootView)
        return rootView
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.images_list)
        noResults = view.findViewById(R.id.no_results)

        val pagingAdapter = ImageListAdapter(diffCallback = ImageItemComparator)

        addNoResultsLoadStateListener(pagingAdapter)

        val adapter = pagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter(pagingAdapter::retry)
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        //recyclerView.isNestedScrollingEnabled = true

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imagesFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

    }

    private fun addNoResultsLoadStateListener(pagingAdapter: ImageListAdapter) {
        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading
                && loadState.append.endOfPaginationReached
                && pagingAdapter.itemCount < 1
            ) {
                recyclerView.visibility = View.GONE
                noResults.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noResults.visibility = View.GONE
            }
        }
    }

}