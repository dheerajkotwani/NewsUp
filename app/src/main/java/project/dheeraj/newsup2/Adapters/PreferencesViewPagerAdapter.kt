package project.dheeraj.newsup2.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import project.dheeraj.newsup2.Activities.SingleNewsActivity
import project.dheeraj.newsup2.Model.NewsHeadlines
import project.dheeraj.newsup2.R

class PreferencesViewPagerAdapter(var context: Context, var articleList: List<NewsHeadlines>): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return articleList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.item_pager_news, container, false)

        val image = view.findViewById<ImageView>(R.id.image_preference)
        val text = view.findViewById<TextView>(R.id.text_preferences)

        Glide.with(context)
            .load(articleList.get(position).urlToImage)
            .into(image)

        text.text = articleList.get(position).title

        view.setOnClickListener {
            val intent = Intent(context, SingleNewsActivity::class.java);
            intent.putExtra(context.getString(R.string.content), articleList[position].content)
            intent.putExtra(context.getString(R.string.description), articleList[position].description)
            intent.putExtra(context.getString(R.string.author), articleList[position].author)
            intent.putExtra(context.getString(R.string.url), articleList[position].url)
            intent.putExtra(context.getString(R.string.urlToImage), articleList[position].urlToImage)
            intent.putExtra(context.getString(R.string.title), articleList[position].title)
            intent.putExtra(context.getString(R.string.publishedAt), articleList[position].publishedAt)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        container.removeView(`object` as View?)
    }


}