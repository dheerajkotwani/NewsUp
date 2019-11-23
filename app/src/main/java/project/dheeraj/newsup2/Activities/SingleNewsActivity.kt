package project.dheeraj.newsup2.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso
import project.dheeraj.newsup2.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

        title.setText(intent.getStringExtra(getString(R.string.title)))
        description.setText(intent.getStringExtra(getString(R.string.description)))
        content.setText(intent.getStringExtra(getString(R.string.content)))
//        date.setText(intent.getStringExtra(getString(R.string.publishedAt)))
        date.setText(convertISOTimeToDate(intent.getStringExtra(getString(R.string.publishedAt))))
//        convertISOTimeToDate(intent.getStringExtra(getString(R.string.publishedAt)))

        Picasso.get()
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


    fun convertISOTimeToDate(isoTime: String): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        var formattedTime: String = "10:00 AM"
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("MMM d, yyyy").format(convertedDate)
            formattedTime = SimpleDateFormat("HH:mm a").format(convertedDate)

            if(formattedTime.subSequence(6,8).toString().equals("PM") && formattedTime.subSequence(0,2).toString().toInt()>12){
                formattedTime = (formattedTime.subSequence(0,2).toString().toInt()-12).toString()+formattedTime.subSequence(2,8).toString()
            }
            if (formattedTime.subSequence(0,2).toString().equals("00")){
                formattedTime = (formattedTime.subSequence(0,2).toString().toInt()+1).toString()+formattedTime.subSequence(2,8).toString()

            }
            if (formattedTime.subSequence(0,2).toString().equals("0:")){
                formattedTime = (formattedTime.subSequence(0,1).toString().toInt()+1).toString()+formattedTime.subSequence(2,8).toString()

            }


            Log.d("Date ", formattedDate+" | "+formattedTime)
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("Error Date ", e.message)
        }
        return formattedDate+" | "+formattedTime
    }

}
