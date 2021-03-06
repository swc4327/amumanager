package com.awesome.amumanager.presentation.ui.main.view.fragment

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
import com.awesome.amumanager.domain.model.Menu
import com.awesome.amumanager.presentation.ui.base.BaseActivity
import com.awesome.amumanager.presentation.ui.base.BaseFragment
import com.awesome.amumanager.presentation.ui.main.adapter.MenuAdapter
import com.awesome.amumanager.presentation.ui.main.view.AddMenuActivity
import com.awesome.amumanager.presentation.ui.main.view.MenuDetailActivity
import com.awesome.amumanager.presentation.ui.main.viewmodel.MenuViewModel
import com.awesome.amumanager.presentation.ui.main.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment() : BaseFragment() {
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private var menuAdapter: MenuAdapter? = null
    private var storeId: String? = ""
    private lateinit var menuViewModel : MenuViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeId = arguments?.getString("store_id")
        menuViewModel = ViewModelProvider(this, viewModelFactory).get(MenuViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initRecyclerView()
        observe()
    }

    override fun onResume() {
        super.onResume()
        menuAdapter?.clearMenus()
        storeId?.let {storeId-> menuViewModel.getMenu(FIRST_CALL, storeId) }
    }

    private fun initRecyclerView() {
        menu_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

                if (!recyclerView.canScrollVertically((1)) && lastVisibleItemPosition >= 0) {
                    menuAdapter?.getLastMenuId(lastVisibleItemPosition)?.let {lastId-> storeId?.let {storeId->
                        menuViewModel.getMenu(lastId, storeId)
                    } }
                }
            }
        })
    }

    private fun initListener() {
        add_menu.setOnClickListener {
            storeId?.let { storeId -> AddMenuActivity.startActivity(requireContext() as BaseActivity, storeId) }

        }
    }

    private fun observe() {
        menuViewModel.menus.observe(viewLifecycleOwner, Observer<ArrayList<Menu>> {menus ->
            if (menuAdapter == null) {
                menuAdapter = MenuAdapter(arrayListOf() , Glide.with(this)) {menu->
                    storeId?.let {storeId -> MenuDetailActivity.startActivity(requireContext() as BaseActivity, menu, storeId) }

                }
                menu_list.adapter = menuAdapter
            }
            menuAdapter?.update(menus)
        })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == ADD_MENU_ACTIVITY || requestCode ==MENU_DETAIL_ACTIVITY) {
//            if(resultCode == AppCompatActivity.RESULT_OK) {
//                menuAdapter?.clearMenus()
//                menuViewModel.getMenu(FIRST_CALL_GET_MENU)
//            }
//        }
//    }
}