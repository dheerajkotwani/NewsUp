package project.dheeraj.newsup2.Activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import project.dheeraj.newsup2.R

class TopStoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stories_activity)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val gifImage = findViewById<ImageView>(R.id.no_internet_image)

        Glide.with(this)
            .load(R.drawable.no_internet_gif_2)
            .into(gifImage)


    }
}
