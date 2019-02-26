package padgett.com.oboeringtones

import android.os.Environment
import java.nio.file.Files
import android.os.Environment.DIRECTORY_ALARMS
import android.os.Environment.getExternalStoragePublicDirectory






    fun requestPermission() {

    }

    fun hasPermission(): Boolean {

        return false;
    }


    fun copyRawSoundFile(): Boolean {

        // this is where we are going to copy our raw resource sound file
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)

        // Make sure the directory exists
        //noinspection ResultOfMethodCallIgnored
        path.mkdirs()

        println("path: $path");
        println("absolute path: + ${path.absolutePath}")
        println("can read: ${path.canRead()}")

        return false

    }




