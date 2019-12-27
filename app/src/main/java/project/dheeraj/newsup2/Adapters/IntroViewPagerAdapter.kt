package project.dheeraj.newsup2.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import project.dheeraj.newsup2.R

class IntroViewPagerAdapter (val context: Context): SliderViewAdapter<SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_intro_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
    }

}

class SliderViewHolder(itemView: View): SliderViewAdapter.ViewHolder(itemView) {

}