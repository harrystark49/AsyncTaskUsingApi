package com.example.asynctaskusingapi

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.SSLSessionCache
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var retro: retro_interface
    lateinit var tv: TextView
    lateinit var context: Context
    lateinit var progress: ProgressDialog
    lateinit var infoData: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.instructions)
        infoData = "sgfdf"
        var s = findViewById<TextView>(R.id.instructions)
        context = this
        getq().execute()
    }

    fun isNetworkAvailable(): Boolean {


        var connectivitymanager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activenetworkinfo = connectivitymanager.activeNetworkInfo
        return activenetworkinfo != null && activenetworkinfo.isConnected
    }

    internal inner class getq() : AsyncTask<Void, Void, String>(){


        override fun onPreExecute() {
            super.onPreExecute()
            progress = ProgressDialog(this@MainActivity)
            progress.setMessage("Loading")
        }

        override fun doInBackground(vararg params: Void?): String {

            var ss = " "
            if (isNetworkAvailable()) {
                var urll = "https://jsonplaceholder.typicode.com/posts/1/"
                retro = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(urll)
                    .build()
                    .create(retro_interface::class.java)

                lifecycleScope.launch {
                    infoData=retro.getdata().body
                    Log.d("bakaa", " data is $infoData")
                }

            }
            Thread.sleep(500L)
//            runBlocking {
//                delay(500)
//            }
            return infoData
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progress.dismiss()
            tv.setText(result)
        }

    }
}