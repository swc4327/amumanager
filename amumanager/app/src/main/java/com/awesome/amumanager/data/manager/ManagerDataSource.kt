package com.awesome.amumanager.data.manager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.service.ManagerApi
import com.awesome.amumanager.domain.model.Manager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface ManagerDataSource {
    fun addManager(manager: Manager, status: MutableLiveData<Int>)

}

class AmuManagerManagerDataSource @Inject constructor(
    private val managerApi: ManagerApi
) : ManagerDataSource {
    override fun addManager(manager: Manager, status: MutableLiveData<Int>) {
        managerApi.addManager(manager)
            .enqueue(object : Callback<DefaultResponse> {

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("retrofit add manager", "실패")
                    Log.e("Check", t.toString())
                }
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                        Log.e("JoinInfoActivity", "success")
                        status.value = 200

                    } else {
                        Log.e("JoinInfoActivity", "실패")
                    }
                }
            })
    }


}