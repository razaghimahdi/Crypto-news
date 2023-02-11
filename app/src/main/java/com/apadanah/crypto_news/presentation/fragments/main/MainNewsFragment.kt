package com.apadanah.crypto_news.presentation.fragments.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apadanah.crypto_news.R
import com.apadanah.crypto_news.business.domain.model.main.News
import com.apadanah.crypto_news.business.domain.model.main.TICKERS_LIST
import com.apadanah.crypto_news.business.domain.util.ProgressBarState
import com.apadanah.crypto_news.business.domain.util.StateMessageCallback
import com.apadanah.crypto_news.databinding.MainNewsFragmentBinding
import com.apadanah.crypto_news.presentation.fragments.BaseFragment
import com.apadanah.crypto_news.presentation.fragments.main.adapter.MainNewsAdapter
import com.apadanah.crypto_news.presentation.fragments.main.filter.filterFragmentBottomSheet
import com.apadanah.crypto_news.presentation.fragments.main.state.MainNewsEvent
import com.apadanah.crypto_news.presentation.fragments.main.state.MainNewsState
import com.apadanah.crypto_news.presentation.util.animation.*
import com.apadanah.crypto_news.presentation.util.base.processQueue
import com.apadanah.crypto_news.presentation.util.custom_view.CustomChip
import com.apadanah.crypto_news.presentation.util.dialogs.informationDialog
import com.google.android.material.chip.ChipDrawable
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("LongLogTag")
class MainNewsFragment : BaseFragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private val TAG = "AppDebug MainNewsFragment"

    private var _binding: MainNewsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainNewsViewModel by viewModel()

    private var newsAdapter: MainNewsAdapter? = null // can leak memory so need to null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainNewsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener(this)
        initRecyclerView()
        subscribeObserver()
        allOnClickListener()
        searchBar()


    }


    private fun subscribeObserver() {


        // handle dialogs
        viewModel.stateQueueUIComponent.observe(viewLifecycleOwner) { state ->
            processQueue(
                context = requireActivity(),
                queue = state,
                stateMessageCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(MainNewsEvent.OnRemoveHeadFromQueue)
                    }
                })
        }


        viewModel.state.observe(viewLifecycleOwner) { state ->

            newsAdapter?.apply {
                submitList(state.newsList)
            }

            uiCommunicationListener.displayProgressBar(state.progressBarState)

            checkIfListIsEmpty(state)



            initTickersAdapter(state.suggestTickers)

        }
    }


    private fun showInformationDialog() {
        requireActivity().informationDialog()
    }


    private fun searchBar() {

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                cacheState()
                viewModel.onTriggerEvent(MainNewsEvent.GetNewsList)
                uiCommunicationListener.hideSoftKeyboard()
            }
            true
        }

        binding.relativeSearch.setOnClickListener {
            activeSearchBar()
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //  suggestTickersAdapter?.filter?.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })


    }

    private fun activeSearchBar() {
        binding.relativeSearch.visibility = View.GONE
        binding.edtSearch.requestFocus()
        binding.edtSearch.showDropdownNow()
        toggleImageSrc(
            view = binding.imgViewSearch,
            drawable = R.drawable.ic_baseline_close_24
        )
    }

    private fun deActiveSearchBar() {


        uiCommunicationListener.hideSoftKeyboard()

        binding.edtSearch.isFocusableInTouchMode = false
        binding.edtSearch.isFocusable = false
        binding.edtSearch.isFocusableInTouchMode = true
        binding.edtSearch.isFocusable = true

        binding.relativeSearch.visibility = View.VISIBLE
        binding.edtSearch.setText("")
        binding.chipGroup.removeViews(1, binding.chipGroup.childCount - 1)

        // first we check if there is not empty in search box then we gonna make it empty and get news again
        if (!viewModel.state.value?.insertedTickers.isNullOrEmpty() ||
            !viewModel.state.value?.selectedTickers.isNullOrEmpty()
        ) {
            viewModel.onTriggerEvent(MainNewsEvent.ResetSearch)
            viewModel.onTriggerEvent(MainNewsEvent.GetNewsList)
        }


        toggleImageSrc(
            view = binding.imgViewSearch,
            drawable = R.drawable.ic_baseline_search_24
        )

    }

    private fun allOnClickListener() {

        binding.imageViewInfo.setOnClickListener {
            showInformationDialog()
        }

        binding.btnShowFilter.setOnClickListener {


            viewModel.state.value?.let { state ->
                this.filterFragmentBottomSheet(
                    state = state,
                    events = viewModel::onTriggerEvent
                )
            }

        }

        binding.imgViewSearch.setOnClickListener {
            deActiveSearchBar()
        }
    }

    private fun initTickersAdapter(list: List<String>) {

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, list)
        binding.edtSearch.setAdapter(adapter)
        binding.edtSearch.threshold = 0
        binding.edtSearch.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selected = parent.getItemAtPosition(position) as String
                val pos: Int = TICKERS_LIST.indexOf(selected)
                binding.edtSearch.showDropdownNow()
                viewModel.onTriggerEvent(MainNewsEvent.AddOrRemoveSelectedTickersToList(selected))
                listItemClickedTickers(selected)
                binding.edtSearch.setText("")
                viewModel.onTriggerEvent(MainNewsEvent.GetNewsList)
                uiCommunicationListener.hideSoftKeyboard()
            }

    }

    private fun listItemClickedTickers(selectedItem: String) {
        binding.chipGroup.addView(createTickersChip(requireContext(), selectedItem) as View)
    }

    private fun createTickersChip(
        context: Context,
        name: String,
    ): CustomChip {
        return CustomChip(context).apply {
            text = name
            id = TICKERS_LIST.indexOf(name)
            val chipDrawable = ChipDrawable.createFromAttributes(
                context,
                null,
                0,
                R.style.Chip_Choice_Cancelable
            )
            this.setEnsureMinTouchTargetSize(false)
            this.setChipDrawable(chipDrawable)
            // this.setTextColor(resources.getColor(R.color.white))
            //  this.closeIconTint = ColorStateList.valueOf(resources.getColor(R.color.grey_500))

            this.setOnCheckedChangeListener { compoundButton, b ->
                binding.chipGroup.removeView(this)
                viewModel.onTriggerEvent(MainNewsEvent.AddOrRemoveSelectedTickersToList(name))
                viewModel.onTriggerEvent(MainNewsEvent.GetNewsList)
            }

        }

    }


    private fun initRecyclerView() {
        binding.recyclerView.apply {

            layoutManager = LinearLayoutManager(requireContext())

            setHasFixedSize(false)

            newsAdapter = MainNewsAdapter(requireContext()) { selectedItem: News ->
                listItemClickedNews(
                    selectedItem
                )
            }


            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("LongLogTag")
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (
                    // lastPosition == newsAdapter?.itemCount?.minus(1)
                        lastPosition > adapter?.itemCount?.div(2)?.toInt()!!
                        && viewModel.state.value?.progressBarState == ProgressBarState.Idle
                        && viewModel.state.value?.isPagingAvailable == true
                    ) {
                        Log.d(TAG, "onScrollStateChanged: attempting to load next page...")
                        viewModel.onTriggerEvent(MainNewsEvent.GetNextPage)
                    }
                }
            })

            adapter = newsAdapter
        }
    }

    private fun listItemClickedNews(selectedItem: News) {
        findNavController().navigate(
            R.id.action_mainNewsFragment_to_detailNewsFragment,
            bundleOf(selectedItem.news_url to "news_url")
        )
    }

    private fun checkIfListIsEmpty(state: MainNewsState) {
        if (state.progressBarState == ProgressBarState.Idle && state.newsList.isEmpty()) {
            binding.emptyList.parent.visibility = View.VISIBLE
        } else {
            binding.emptyList.parent.visibility = View.GONE
        }
    }


    private fun cacheState() {
        val insertedTickers = binding.edtSearch.text.toString().trim()
        viewModel.onTriggerEvent(MainNewsEvent.OnUpdateInsertedTickers(insertedTickers))
    }

    override fun onPause() {
        super.onPause()
        cacheState()
    }


    override fun onRefresh() {
        viewModel.onTriggerEvent(MainNewsEvent.RefreshFailedNetwork)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
    }

}