package project.dheeraj.newsup2.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import project.dheeraj.newsup2.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val image = findViewById<ImageView>(R.id.splash_logo)
        val text = findViewById<TextView>(R.id.splash_text)
        val firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            if (firebaseAuth.currentUser==null)
                startActivity(Intent(this, LoginActivity::class.java))
            else
                startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, 2500)

        val alphaAnimation = AlphaAnimation(0.2f,1.0f)
        val alphaAnimation2 = AlphaAnimation(0.0f,1.0f)
        alphaAnimation.startOffset = 200
        alphaAnimation.duration = 800
        image.animation = alphaAnimation

        alphaAnimation2.startOffset = 1000
        alphaAnimation2.duration = 800
        text.animation = alphaAnimation2
    }
}
