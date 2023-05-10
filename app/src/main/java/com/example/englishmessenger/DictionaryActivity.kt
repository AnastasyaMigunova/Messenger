package com.example.englishmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.englishmessenger.databinding.ActivityChatLogBinding
import com.example.englishmessenger.databinding.ActivityDictionaryBinding
import com.example.englishmessenger.retrofit.LanguagetoolApi
import com.example.englishmessenger.retrofit.RapidApi
import com.example.englishmessenger.retrofit.RapidPostClass
import kotlinx.android.synthetic.main.activity_dictionary.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class DictionaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDictionaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level =  HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://text-translator2.p.rapidapi.com/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val rapidApi = retrofit.create(RapidApi::class.java)

        binding.buttonTranslate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //val NewText = rapidApi.transText("en", "ru", "Hello World")
                val NewText = rapidApi.transText("en", "it", binding.editTextEng.text.toString())
                runOnUiThread {
                    textViewRus.text = NewText.text
                }
            }
        }


    }
}