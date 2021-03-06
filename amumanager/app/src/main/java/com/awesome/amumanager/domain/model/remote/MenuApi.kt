//package com.awesome.amumanager.domain.model.remote
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import com.awesome.amumanager.data.api.response.DefaultResponse
//import com.awesome.amumanager.data.api.response.MenuResponse
//import com.awesome.amumanager.domain.model.Menu
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class MenuApi {
//
//    fun getMenu(
//        menus: MutableLiveData<ArrayList<Menu>>,
//        storeId: String,
//        lastId: String
//    ) {
//
//        val joinApi = RetrofitObject.MENU_API
//
//        joinApi.getMenuList(storeId.toString(), lastId)
//                .enqueue(object : Callback<MenuResponse> {
//
//                    override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
//                        Log.e("Menu Retrofit getMenu", "실패")
//                        Log.e("Check", t.toString())
//                    }
//
//                    override fun onResponse(
//                            call: Call<MenuResponse>,
//                            response: Response<MenuResponse>
//                    )  {
//                        println(response)
//                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                            Log.e("Get menu Retrofit", "success")
//                            menus.value = response.body()!!.menus
//                        } else {
//
//                        }
//                    }
//                })
//    }
//
//    fun addMenu(menu: Menu, status: MutableLiveData<Int>) {
//        val joinApi = RetrofitObject.MENU_API
//
//        joinApi.addMenu(menu)
//                .enqueue(object : Callback<DefaultResponse> {
//
//                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Log.e("Retrofit Add Menu", "실패")
//                        Log.e("Check", t.toString())
//                    }
//                    override fun onResponse(
//                            call: Call<DefaultResponse>,
//                            response: Response<DefaultResponse>
//                    )  {
//                        println(response)
//                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                            Log.e("AddMenuActivity", "success")
//                            status.value = 200
//                        } else {
//                            Log.e("AddMenuActivity", "실패")
//                        }
//                    }
//                })
//    }
//
//    fun deleteMenu(menuId: String, status : MutableLiveData<Int>) {
//        val joinApi = RetrofitObject.MENU_API
//
//        joinApi.deleteMenu(menuId)
//            .enqueue(object : Callback<DefaultResponse> {
//
//                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                    Log.e("Retrofit delete Menu", "실패")
//                    Log.e("Check", t.toString())
//                }
//                override fun onResponse(
//                    call: Call<DefaultResponse>,
//                    response: Response<DefaultResponse>
//                )  {
//                    println(response)
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("delete Menu", "success")
//                        status.value = 200
//                    } else {
//                        Log.e("delete menu", "실패")
//                    }
//                }
//            })
//    }
//}