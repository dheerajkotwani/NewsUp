package project.dheeraj.newsup2.Adapters

import android.app.Activity
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.R

class TopStoriesHomeRecyclerViewAdapter(var context : Context, var newsheadlines : List<NewsHeadlines>) : RecyclerView.Adapter<TopStoriesHomeViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesHomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_round_top_headlines, parent, false)
        val viewHolder : TopStoriesHomeViewHolder = TopStoriesHomeViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return newsheadlines.size
    }

    override fun onBindViewHolder(holder: TopStoriesHomeViewHolder, position: Int) {

        if (newsheadlines.get(position).name != null) {
            holder.text.setText(newsheadlines.get(position).name)
        }
        else if (newsheadlines.get(position).author != null){
            holder.text.setText(newsheadlines.get(position).author)
        }
        else if (newsheadlines.get(position).id != null){
            holder.text.setText(newsheadlines.get(position).id)
        }
        else {
            holder.text.setText(newsheadlines.get(position).title)
        }

        Picasso.get()
            .load(newsheadlines.get(position).urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)


    }

}

class TopStoriesHomeViewHolder(itemView: View) : ViewHolder(itemView) {

    val image : ImageView = itemView.findViewById(R.id.image_view_top_headlines_round)
    val text : TextView = itemView.findViewById(R.id.text_view_top_headlines_round)


}
