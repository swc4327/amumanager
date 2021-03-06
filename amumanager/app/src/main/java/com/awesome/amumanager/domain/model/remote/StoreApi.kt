//package com.awesome.amumanager.domain.model.remote
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import com.awesome.amumanager.data.api.response.DefaultResponse
//import com.awesome.amumanager.domain.model.Store
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class StoreApi {
//
////    fun getStore(
////        stores: MutableLiveData<ArrayList<Store>>,
////        uid: String,
////        lastId: String
////    ) {
////
////        val joinApi = RetrofitObject.storeService
////
////        joinApi.getStore(uid, lastId)
////                .enqueue(object : Callback<StoreResponse> {
////
////                    override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
////                        Log.e("Main Retrofit getStore", "실패")
////                    }
////
////                    override fun onResponse(
////                            call: Call<StoreResponse>,
////                            response: Response<StoreResponse>
////                    ) {
////                        println(response)
////                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
////                            stores.value = response.body()!!.stores
////                        } else {
////
////                        }
////                    }
////                })
////    }
//
//    fun addStore(store : Store, status : MutableLiveData<Int>) {
//        val joinApi = RetrofitObject.STORE_API
//
//        joinApi.addStore(store)
//                .enqueue(object : Callback<DefaultResponse> {
//
//                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Log.e("Retrofit Add Store", "실패")
//                        Log.e("Check", t.toString())
//                    }
//                    override fun onResponse(
//                            call: Call<DefaultResponse>,
//                            response: Response<DefaultResponse>
//                    )  {
//                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                            Log.e("AddStoreActivity", "success")
//                            status.value = 200
//
//                        } else {
//                            Log.e("AddStoreActivity", "실패")
//                        }
//                    }
//                })
//    }
//
//    fun deleteStore(storeId : String, status : MutableLiveData<Int>) {
//        val joinApi = RetrofitObject.STORE_API
//
//        joinApi.deleteStore(storeId)
//            .enqueue(object : Callback<DefaultResponse> {
//
//                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                    Log.e("Retrofit Delete Store", "실패")
//                    Log.e("Check", t.toString())
//                }
//                override fun onResponse(
//                    call: Call<DefaultResponse>,
//                    response: Response<DefaultResponse>
//                )  {
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("Delete Store", "success")
//                        status.value = 200
//
//                    } else {
//                        Log.e("Delete Store", "실패")
//                    }
//                }
//            })
//    }
//
//    fun openStore(storeId : String, status : MutableLiveData<Int>) {
//        val joinApi = RetrofitObject.STORE_API
//
//        joinApi.openStore(storeId)
//            .enqueue(object : Callback<DefaultResponse> {
//
//                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                    Log.e("Retrofit Open Store", "실패")
//                    Log.e("Check", t.toString())
//                }
//                override fun onResponse(
//                    call: Call<DefaultResponse>,
//                    response: Response<DefaultResponse>
//                )  {
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("Open Store", "success")
//                        status.value = 200
//                    } else {
//                        Log.e("Open Store", "실패")
//                    }
//                }
//            })
//    }
//
//    fun closeStore(storeId : String, status : MutableLiveData<Int>) {
//        val joinApi = RetrofitObject.STORE_API
//
//        joinApi.closeStore(storeId)
//            .enqueue(object : Callback<DefaultResponse> {
//
//                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                    Log.e("Retrofit Close Store", "실패")
//                    Log.e("Check", t.toString())
//                }
//                override fun onResponse(
//                    call: Call<DefaultResponse>,
//                    response: Response<DefaultResponse>
//                )  {
//                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
//                        Log.e("Close Store", "success")
//                        status.value = 200
//                    } else {
//                        Log.e("Close Store", "실패")
//                    }
//                }
//            })
//    }
//}