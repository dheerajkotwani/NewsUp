package project.dheeraj.newsup2.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import project.dheeraj.newsup2.R
import project.dheeraj.newsup2.Util.UtilMethods.convertISOTime

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SingleNewsActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)


        val image = findViewById<ImageView>(R.id.news_full_image)
        val title = findViewById<TextView>(R.id.news_full_headline_text)
        val description = findViewById<TextView>(R.id.news_full_description_text)
        val content = findViewById<TextView>(R.id.news_full_content_text)
        val date = findViewById<TextView>(R.id.news_full_date_text)
        val fabBookmarkBorder = findViewById<FloatingActionButton>(R.id.fab_news_full_bookmark_border)
        val fabBookmarkFilled = findViewById<FloatingActionButton>(R.id.news_full_fab_bookmark_filled)
        val fabShare = findViewById<FloatingActionButton>(R.id.news_full_fab_share)

        title.text = intent.getStringExtra(getString(R.string.title))
        description.text = intent.getStringExtra(getString(R.string.description))!!.substringBeforeLast('[')
        content.text = intent.getStringExtra(getString(R.string.content))
        date.text = convertISOTime(applicationContext, intent.getStringExtra(getString(R.string.publishedAt)))

        Glide.with(this)
            .load(intent.getStringExtra(getString(R.string.urlToImage)))
            .placeholder(R.drawable.index)
            .into(image)

        fabBookmarkBorder.setOnClickListener {
            Toast.makeText(this, getString(R.string.bookmarkAdded), Toast.LENGTH_SHORT).show()
            fabBookmarkBorder.visibility = View.GONE
            fabBookmarkFilled.visibility = View.VISIBLE
        }

        fabBookmarkFilled.setOnClickListener {
            Toast.makeText(this, getString(R.string.bookmarkRemoved), Toast.LENGTH_SHORT).show()
            fabBookmarkBorder.visibility = View.VISIBLE
            fabBookmarkFilled.visibility = View.GONE
        }

        fabShare.setOnClickListener {
            Toast.makeText(this, getString(R.string.share), Toast.LENGTH_SHORT).show()

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "*${intent.getStringExtra(getString(R.string.title))}*\n\n" +
                        "${intent.getStringExtra(getString(R.string.description))}\n\n" +
                        "To read full news visit:\n${intent.getStringExtra(getString(R.string.url))}\n\n" +
                        "*Sent from NewsUp Android App: Kotlin*\n*Developer: Dheeraj*")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

    }

}
