package com.oip.carapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.createNotificationChannel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        createNotificationChannel(this)

        RetrofitClient.retrofitClientTwo.getTest("Lunch")
            .enqueue(object : Callback<Any> {
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    val menu =
                        JSONObject(JSONObject(Gson().toJson(response.body())).getJSONArray("menu1")[0].toString())
                    val panelKeys = menu.keys()

                    while (panelKeys.hasNext()) {
                        val panel = menu.getJSONObject(panelKeys.next()) // get key from list
                        val rank = panel.getString("rank")
                        val dishname = panel.getString("dish_name")
                        Log.d("SplashActivity", "Response id: $rank dish name: $dishname")
                    }

                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    t.printStackTrace()
                }

            })

//        Handler(Looper.getMainLooper()).postDelayed({
//
//            val intent = if (PreferencesHandler.getIsLogin()) {
//                Intent(this, MainActivity::class.java)
//            } else {
//                Intent(this, LoginActivity::class.java)
//            }
//            startActivity(intent)
//            finish()
//        }, 1000)
    }

}