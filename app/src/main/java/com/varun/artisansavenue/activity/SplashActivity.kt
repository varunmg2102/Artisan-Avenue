import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.varun.artisansavenue.MainActivity
import com.varun.artisansavenue.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMEOUT: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app's main activity
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)

            // Close this activity
            finish()
        }, SPLASH_TIMEOUT)
    }
}
