package com.awesome.amumanager.presentation.ui.main.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.amumanager.R
import com.awesome.amumanager.domain.model.Constants.FIRST_CALL
import com.awesome.amumanager.domain.model.ReserveList
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.base.BaseFragment
import com.awesome.amumanager.presentation.ui.main.adapter.ReserveAdapter
import com.awesome.amumanager.presentation.ui.main.view.ReserveDetailActivity
import com.awesome.amumanager.presentation.ui.main.viewmodel.ReserveViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_reserve.*
import javax.inject.Inject

class ReserveFragment() : BaseFragment() {
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private var reserveAdapter: ReserveAdapter? = null
    private var storeId: String? = ""
    private lateinit var reserveViewModel : ReserveViewModel

    private var isFabOpen = false
    private var showConfirmed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeId = arguments?.getString("store_id")

        reserveViewModel = ViewModelProvider(this, viewModelFactory).get(ReserveViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reserve, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initRecyclerView()
        observe()
    }

    override fun onResume() {
        super.onResume()
        if(showConfirmed) {
            reserveAdapter?.clearReserveLists()
            storeId?.let {storeId-> reserveViewModel.getConfirmedReserveList(FIRST_CALL, storeId) }
        } else {
            reserveAdapter?.clearReserveLists()
            storeId?.let {storeId-> reserveViewModel.getReserveList(FIRST_CALL, storeId) }
        }
    }

    private fun initListener() {
        sort_button.setOnClickListener {
            toggleFab()
        }

        confirmedReserve.setOnClickListener {
            if(!showConfirmed) {
                reserveAdapter?.clearReserveLists()
                storeId?.let { storeId -> reserveViewModel.getConfirmedReserveList(FIRST_CALL, storeId) }
                showConfirmed = !showConfirmed
            } else {
                reserveAdapter?.clearReserveLists()
                storeId?.let { storeId -> reserveViewModel.getReserveList(FIRST_CALL, storeId) }
                showConfirmed = !showConfirmed
            }

        }

    }

    private fun toggleFab() {
        if(isFabOpen) {
            ObjectAnimator.ofFloat(confirmedReserve, "translationY", 0f).apply { start()}

        } else {
            ObjectAnimator.ofFloat(confirmedReserve, "translationY", -80f).apply { start()}
        }
        isFabOpen = !isFabOpen
    }

    private fun observe() {
        reserveViewModel.reserveLists.observe(viewLifecycleOwner, Observer<ArrayList<ReserveList>> {reserveLists->
            if(reserveAdapter == null) {
                reserveAdapter = ReserveAdapter(arrayListOf(), Glide.with(this)) {reserveList->
                    storeId?.let {storeId -> ReserveDetailActivity.startActivity(requireContext() as BaseActivity, reserveList, storeId) }
                }
                reserve_list.adapter = reserveAdapter
            }
            reserveAdapter?.update(reserveLists)
        })
    }

    private fun initRecyclerView() {
        reserve_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    if(showConfirmed) {
                        reserveAdapter?.getLastReserveId(lastVisibleItemPosition)?.let {lastId-> storeId?.let {storeId->
                            reserveViewModel.getConfirmedReserveList(lastId,
                                storeId
                            )
                        } }
                    } else {
                        reserveAdapter?.getLastReserveId(lastVisibleItemPosition)?.let {lastId-> storeId?.let {storeId->
                            reserveViewModel.getReserveList(lastId,
                                storeId
                            )
                        } }
                    }
                }
            }
        })

    }
}


//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//    if(requestCode ==RESERVE_DETAIL_ACTIVITY) {
//        if(resultCode == AppCompatActivity.RESULT_OK) {
//            reserveAdapter?.clearReserveLists()
//            reserveViewModel.getReserveList(FIRST_CALL_GET_RESERVE)
//        }
//    }
//}