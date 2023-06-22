package com.example.fail


//  https://api.openweathermap.org/data/2.5/forecast?q=dhaka&appid=de33007892afe70f255295c3b4512588&units=metric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL="https://api.openweathermap.org/data/2.5/"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FetchWeatherData()
    }

    private fun FetchWeatherData(){
        val retrofitbuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData=retrofitbuilder.getWeatherData("dhaka","de33007892afe70f255295c3b4512588","metric")

        retrofitData.enqueue(object : Callback<MainDataItem>{
            override fun onResponse(call: Call<MainDataItem>, response: Response<MainDataItem>) {
                val responseBody=response.body()!!

                //For temperature
                val txt=findViewById<TextView>(R.id.txt)
                val temp=responseBody.list[0].main.temp.toString()
                val cel=" ℃"
                txt.text=temp+cel

                //For weather
                val weather=findViewById<TextView>(R.id.weather)
                weather.text=responseBody.list[0].weather[0].description

                //Feels Like
                val feels=findViewById<TextView>(R.id.feels)
                val like="Feels Like "
                val tempfeel=responseBody.list[0].main.feels_like.toString()
                feels.text=like+tempfeel+" ℃"

                //Min Temp and Max Temp
                val mintemp=findViewById<TextView>(R.id.mintemp)
                val min="Min Temperature "
                val mit=responseBody.list[0].main.temp_min
                mintemp.text=min+mit+" ℃"

                val maxtemp=findViewById<TextView>(R.id.maxtemp)
                val max="Max Temperature "
                val mat=responseBody.list[0].main.temp_max
                maxtemp.text=max+mat+" ℃"

                //Humidity
                val hum=findViewById<TextView>(R.id.humidity)
                hum.text=responseBody.list[0].main.humidity.toString()

                //Pressure
                val pres=findViewById<TextView>(R.id.pressure)
                pres.text=responseBody.list[0].main.pressure.toString()

                //Wind
                val wind=findViewById<TextView>(R.id.wind)
                wind.text=responseBody.list[0].wind.speed.toString()

            }

            override fun onFailure(call: Call<MainDataItem>, t: Throwable) {
                Log.d("Fail","Couldn't load data")
            }

        })
    }
}