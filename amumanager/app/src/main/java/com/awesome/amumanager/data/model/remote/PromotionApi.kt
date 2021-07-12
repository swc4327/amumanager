package com.awesome.amumanager.data.model.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.awesome.amumanager.data.api.response.DefaultResponse
import com.awesome.amumanager.data.api.response.PromotionResponse
import com.awesome.amumanager.data.model.Promotion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionApi {

    fun getPromotionList(
            promotionList: MutableLiveData<ArrayList<Promotion>>,
            storeId : String
    ) {

        var promotionListTemp = ArrayList<Promotion>()

        val joinApi = RetrofitObject.getPromotionService

        joinApi.getPromotionList(storeId)
                .enqueue(object : Callback<PromotionResponse> {

                    override fun onFailure(call: Call<PromotionResponse>, t: Throwable) {
                        Log.e("Retrofit get Promotion", "실패")
                    }

                    override fun onResponse(
                            call: Call<PromotionResponse>,
                            response: Response<PromotionResponse>
                    )  {
                        println(response)
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            promotionListTemp.addAll(response.body()!!.promotions)
                            promotionList.value = promotionListTemp
                        } else {

                        }
                    }
                })
    }


    fun addPromotion(promotion : Promotion , status : MutableLiveData<Int>) {
        val joinApi = RetrofitObject.addPromotionService

        joinApi.addPromotion(promotion)
                .enqueue(object : Callback<DefaultResponse> {

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Log.e("Retrofit Add Promotion", "실패")
                        Log.e("Check", t.toString())
                    }
                    override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                    )  {
                        if (response.isSuccessful && response.body() != null && response.body()!!.code == 200) {
                            Log.e("AddPromotionActivity", "success")
                            status.value = 200
                        } else {
                            Log.e("AddPromotionActivity", "실패")
                        }
                    }
                })
    }
}