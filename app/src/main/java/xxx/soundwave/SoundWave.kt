package xxx.soundwave


import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

class SoundWave {
    @Volatile var isStart = false
        private set
    private var audioTrack: AudioTrack? = null
    private val lock = Object()

    fun stop() {
        isStart = false
        synchronized(lock) {}
    }

    fun start(Hz: Int) {
        stop()
        if (Hz <= 0) return
        Thread(Runnable {
            isStart = true
            synchronized(lock) {
                audioTrack = AudioTrack(AudioManager.STREAM_MUSIC, SAMPLING_RATE, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_8BIT, SAMPLING_RATE, AudioTrack.MODE_STREAM)
                audioTrack?.play()
                val wave = sin(ByteArray(SAMPLING_RATE), SAMPLING_RATE / Hz, SAMPLING_RATE)
                while (isStart) audioTrack?.write(wave, 0, SAMPLING_RATE)
                audioTrack?.stop()
                audioTrack = null
            }
        }).start()

    }

    companion object {
        private val SAMPLING_RATE = 44100
        private fun sin(wave: ByteArray, waveLen: Int, length: Int): ByteArray {
            for (i in 0 until length) {
                wave[i] = (127 * (1 - Math.sin(2.0 * 3.1415 * (i % waveLen * 1.00 / waveLen)))).toByte()
            }
            return wave
        }
    }
}
