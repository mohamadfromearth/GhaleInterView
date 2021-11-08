package com.mohamad.ghaleinterview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohamad.ghaleinterview.R
import com.mohamad.ghaleinterview.data.remote.response.dailyWeather.Daily
import com.mohamad.ghaleinterview.databinding.ItemDailyWeatherBinding
import com.mohamad.ghaleinterview.other.Constance.ICON_BASE_URL

class DailyWeatherAdapter: RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

private lateinit var binding:ItemDailyWeatherBinding

    inner class DailyWeatherViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)



    private val differCallback = object : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.humidity == newItem.humidity
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.humidity == newItem.humidity
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_weather,parent,false)
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        binding = ItemDailyWeatherBinding.bind(holder.itemView)
        val daily = differ.currentList[position]
        binding.tvTemp.text = daily.temp.day.toInt().toString() + "\u2109"
        Glide.with(binding.root).load("$ICON_BASE_URL${daily.weather[0].icon}.png").into(binding.ivDailyWeatherIconItem)
        binding.root.setOnClickListener {
            clickListener?.let {
                it(daily)
            }
        }
    }

    private var clickListener:((daily:Daily) -> Unit)? = null
    fun setOnClickListener(listener:(daily:Daily)->Unit){
        clickListener = listener
    }



    fun submitList(dailies:List<Daily>){
        differ.submitList(dailies)
    }

    private val differ = AsyncListDiffer(this,differCallback)

    override fun getItemCount() = differ.currentList.size


}