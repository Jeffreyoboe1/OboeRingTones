package padgett.com.oboeringtones

import android.content.Context
import android.os.Environment
import java.nio.file.Files
import android.os.Environment.DIRECTORY_ALARMS
import android.os.Environment.getExternalStoragePublicDirectory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import android.provider.MediaStore

import android.R.attr.mimeType
import android.content.ContentValues
import android.media.MediaScannerConnection
import android.util.Log





fun requestPermission() {
        //TODO: make sure to request permission.
    }

    fun hasPermission(): Boolean {

        return false;
    }


    fun copyRawSoundFile(context: Context, excerpt: OboeExcerpt, typeOfTone: String): Boolean {



        // this is where we are going to copy our raw resource sound file
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)

        // Make sure the directory exists
        //noinspection ResultOfMethodCallIgnored
        path.mkdirs()

        println("path: $path");
        println("absolute path: + ${path.absolutePath}")
        println("can read: ${path.canRead()}")

        var fileName: String = context.resources.getResourceEntryName(excerpt.musicResource) + ".m4a"

        var destinationFile: File = File(path, fileName)

        var mimeType: String = "audio/m4a"

        // Write the file
        var inputStream: InputStream
        var outputStream: FileOutputStream

        try {

            inputStream = context.resources.openRawResource(excerpt.musicResource)
            outputStream = FileOutputStream(destinationFile)

            // Write in 1024-byte chunks
            val buffer = ByteArray(1024)
            var bytesRead: Int
            // Keep writing until `inputStream.read()` returns -1, which means we reached the
            //  end of the stream
            while ((inputStream.read(buffer)) > 0) {
                bytesRead = inputStream.read(buffer)

                outputStream.write(buffer, 0, bytesRead)
            }

            // Set the file metadata
            val destinationAbsPath = destinationFile.getAbsolutePath()
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DATA, destinationAbsPath)
            contentValues.put(MediaStore.MediaColumns.TITLE, excerpt.title)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)


            when (typeOfTone) {

                MediaStore.Audio.Media.IS_ALARM -> {
                    contentValues.put(MediaStore.Audio.Media.IS_ALARM, true)
                    contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
                    contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, false)
                    contentValues.put(MediaStore.Audio.Media.IS_MUSIC, false)}

                MediaStore.Audio.Media.IS_NOTIFICATION -> {
                    contentValues.put(MediaStore.Audio.Media.IS_ALARM, false)
                    contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, true)
                    contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, false)
                    contentValues.put(MediaStore.Audio.Media.IS_MUSIC, false)}

                MediaStore.Audio.Media.IS_RINGTONE -> {
                    contentValues.put(MediaStore.Audio.Media.IS_ALARM, false)
                    contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
                    contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, true)
                    contentValues.put(MediaStore.Audio.Media.IS_MUSIC, false)}

                else -> println("Error in the when statement")
            }

            val contentUri = MediaStore.Audio.Media.getContentUriForPath(destinationAbsPath)


            // If the ringtone already exists in the database, delete it first
            context.contentResolver.delete(contentUri,
                MediaStore.MediaColumns.DATA + "=\"" + destinationAbsPath + "\"", null)

            // Add the metadata to the file in the database
            val newUri = context.contentResolver.insert(contentUri, contentValues)

            // Tell the media scanner about the new ringtone

            MediaScannerConnection.scanFile(
                context,
                Array(0){newUri.toString()},
                Array(0){mimeType},
                null
            )

            /*
            MediaScannerConnection.scanFile(
                context,
                new String[]{newUri.toString()},
                new String[]{mimeType},
                null
            )
            */

            println("Copied alarm tone ${excerpt.title} to $destinationAbsPath")
            println("ID is $newUri")


        } catch (e: Exception) {

        }

        finally {

        }



        return false

    }




