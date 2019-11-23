package project.dheeraj.newsup2.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.dheeraj.newsup2.Model.SuggestedTopics
import project.dheeraj.newsup2.R

class SuggestedTopicsRecyclerViewAdapter(val context : Context, val suggestedTopics : List<SuggestedTopics>)  : RecyclerView.Adapter<SuggestedTopicsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedTopicsViewHolder {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_category_news, parent, false)
        val viewHolder = SuggestedTopicsViewHolder(view)
        return viewHolder


    }

    override fun getItemCount(): Int {

        return suggestedTopics.size

    }

    override fun onBindViewHolder(holder: SuggestedTopicsViewHolder, position: Int) {

        holder.text.setText(suggestedTopics.get(position).title)
        holder.image.setImageResource(suggestedTopics.get(position).image)
    }
}

class SuggestedTopicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.image)
    val text = itemView.findViewById<TextView>(R.id.text)

}