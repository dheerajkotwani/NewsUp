package project.dheeraj.newsup2.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import project.dheeraj.newsup2.Activities.SingleNewsActivity
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.R

class TopStoriesHomeRecyclerViewAdapter(var context : Context, var newsheadlines : List<NewsHeadlines>) : RecyclerView.Adapter<TopStoriesHomeRecyclerViewAdapter.TopStoriesHomeViewHolder>(){


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

        Glide.with(context)
            .load(newsheadlines.get(position).urlToImage)
            .placeholder(R.drawable.index)
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, SingleNewsActivity::class.java);
            intent.putExtra(context.getString(R.string.content), newsheadlines.get(position).content)
            intent.putExtra(context.getString(R.string.description), newsheadlines.get(position).description)
            intent.putExtra(context.getString(R.string.author), newsheadlines.get(position).author)
            intent.putExtra(context.getString(R.string.url), newsheadlines.get(position).url)
            intent.putExtra(context.getString(R.string.urlToImage), newsheadlines.get(position).urlToImage)
            intent.putExtra(context.getString(R.string.title), newsheadlines.get(position).title)
            intent.putExtra(context.getString(R.string.publishedAt), newsheadlines.get(position).publishedAt)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }


    }

    class TopStoriesHomeViewHolder(itemView: View) : ViewHolder(itemView) {

        val image : ImageView = itemView.findViewById(R.id.image_view_top_headlines_round)
        val text : TextView = itemView.findViewById(R.id.text_view_top_headlines_round)

    }

}
