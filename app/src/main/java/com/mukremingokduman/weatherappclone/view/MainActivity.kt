package com.mukremingokduman.weatherappclone.view

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mukremingokduman.weatherappclone.R
import com.mukremingokduman.weatherappclone.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName","Sakarya")
        edt_city_name.setText(cName)
        viewModel.refreshData(cName!!)

        getLiveData()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data_view.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

        img_search_city_name.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
            Log.i(TAG, "onCreate: " + cityName)

        }


    }

    private fun getLiveData() {
        viewModel.weather_data.observe(this,Observer{ data ->
            data?.let{
                ll_data_view.visibility = View.VISIBLE
                pb_loading.visibility = View.GONE

                tv_degree.text = data.main.temp.toString() + "°C"
                tv_country_code.text = data.sys.country.toString()
                tv_city_code.text = data.name.toString()

                tv_humidity.text = data.main.humidity.toString()
                tv_speed.text = data.wind.speed.toString()
                tv_lat.text = data.coord.lat.toString()
                tv_lon.text = data.coord.lon.toString()

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(img_weather_icon)
            }
        })

        viewModel.weather_loading.observe(this,Observer{ load ->
            load?.let{
                if(it){
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data_view.visibility = View.GONE
                }
                else{
                    pb_loading.visibility = View.GONE
                }
        }

        })

        viewModel.weather_error.observe(this,Observer{ error ->
            error?.let{
                if(it){
                    pb_loading.visibility = View.GONE
                    tv_error.visibility = View.VISIBLE
                    ll_data_view.visibility = View.GONE
                }
                else{
                    tv_error.visibility = View.GONE
                }
        }

        })


    }
}