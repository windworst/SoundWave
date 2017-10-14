package xxx.soundwave

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val soundWave = SoundWave()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            if (soundWave.isStart) {
                button.text = "Start"
                editText.isEnabled = true
                soundWave.stop()
            } else {
                val rate = try {
                    editText.text.toString().toInt()
                } catch (e: Exception) {
                    0
                }
                if (rate <= 0) {
                    Toast.makeText(this@MainActivity, "Input Invalid", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                button.text = "Stop"
                editText.isEnabled = false
                soundWave.start(rate)
            }
        }
    }
}
