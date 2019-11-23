package project.dheeraj.newsup2.Activities

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import project.dheeraj.newsup2.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class SingleNewsActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news)

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
        }

    }

//    fun convertISOTimeToDate(isoTime: String): String? {
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        var convertedDate: Date? = null
//        var formattedDate: String? = null
//        try {
//            convertedDate = sdf.parse(isoTime)
//            formattedDate = SimpleDateFormat("MMMMM dd,yyyy").format(convertedDate)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//
//        return formattedDate
//    }

    fun convertISOTimeToDate(isoTime: String): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        var formattedTime: String? = null
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("MMM d, yyyy").format(convertedDate)
            formattedTime = SimpleDateFormat("HH:mm a").format(convertedDate)
            Log.d("Date ", formattedDate+" | "+formattedTime)
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("Error Date ", e.message)
        }
        return formattedDate+" | "+formattedTime
    }

}
