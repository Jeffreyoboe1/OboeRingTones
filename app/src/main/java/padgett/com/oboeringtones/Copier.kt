package padgett.com.oboeringtones

import android.content.Context
import android.os.Environment
import java.nio.file.Files
import android.os.Environment.DIRECTORY_ALARMS
import android.os.Environment.getExternalStoragePublicDirectory
import java.lang.Exception
import android.provider.MediaStore

import android.R.attr.mimeType
import android.content.ContentValues
import android.media.MediaScannerConnection
import android.util.Log
import android.R.attr.path
import android.content.Intent
import android.R.attr.path
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import java.io.*


fun requestPermission() {
        //TODO: make sure to request permission.
    }

    fun hasPermission(): Boolean {

        return false;
    }


    fun copyRawSoundFile(context: Context, excerpt: OboeExcerpt, typeOfTone: String): Boolean {
        var buffer: ByteArray? = null
        val fileInputStream = context.getResources().openRawResource(excerpt.musicResource)
        var size: Int
        // this is where we are going to copy our raw resource sound file

        try {
            size = fileInputStream.available()
            buffer = ByteArray(size)
            fileInputStream.read(buffer)
            fileInputStream.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
        }

        var directory: String
        var ringtoneManagerType: Int


        when (typeOfTone) {

            MediaStore.Audio.Media.IS_RINGTONE -> {
                directory = Environment.DIRECTORY_RINGTONES
                ringtoneManagerType = RingtoneManager.TYPE_RINGTONE
                }

            MediaStore.Audio.Media.IS_NOTIFICATION -> {
                directory = Environment.DIRECTORY_NOTIFICATIONS
                ringtoneManagerType = RingtoneManager.TYPE_NOTIFICATION
                }

            MediaStore.Audio.Media.IS_ALARM -> {
                directory = Environment.DIRECTORY_ALARMS
                ringtoneManagerType = RingtoneManager.TYPE_ALARM
                }
            else -> {
                directory = Environment.DIRECTORY_RINGTONES
                ringtoneManagerType = RingtoneManager.TYPE_RINGTONE
            }
        }

        val path = Environment.getExternalStoragePublicDirectory(directory).path + "/"
        //val path = "/sdcard/sounds/" // this works
        //val path2 = Environment.getExternalStorageDirectory().path
        //val path3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
        //val path4 = Environment.getExternalStorageDirectory().absolutePath

        println("path1: $path")
        //println("path2: $path2")
        //println("path3: $path3")
        //println("path4 - absolute of path2: $path4")

        val filename = "my_firebird_test" + ".m4a"

        val existing = File(path).exists()
        if (!existing) {
            File(path).mkdirs()
        }

        val save: FileOutputStream
        try {
            save = FileOutputStream(path + filename)
            save.write(buffer)
            save.flush()
            save.close()
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
        } catch (e: IOException) {
            // TODO Auto-generated catch block
        }

        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$path$filename")))

        val k = File(path, filename)

        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DATA, k.absolutePath)
        values.put(MediaStore.MediaColumns.TITLE, "Not Default ${excerpt.title}")
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/m4a")
        values.put(MediaStore.Audio.Media.ARTIST, "This is Where Artist goes")
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true)
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
        values.put(MediaStore.Audio.Media.IS_ALARM, false)
        values.put(MediaStore.Audio.Media.IS_MUSIC, false)

        val uri = MediaStore.Audio.Media.getContentUriForPath(k.absolutePath)
        context.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + k.absolutePath + "\"", null)
        val newUri = context.getContentResolver().insert(uri, values)


        try {
            RingtoneManager.setActualDefaultRingtoneUri(context, ringtoneManagerType, newUri)
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.System.canWrite(context)) {
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                        .setData(Uri.parse("package:" + context.getPackageName()))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }



        Toast.makeText(context, "Done on copier page.", Toast.LENGTH_SHORT).show()
        return true












/*

        var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).absolutePath
        path = "/sdcard/sounds/"

        var exStoragePath = Environment.getExternalStorageDirectory().absolutePath
        var newPath = exStoragePath + "/media/alarms/"

        println("path is actually: $path")
        println("path exStoragePath is: $exStoragePath")
        println("newPath is: $newPath")

        // Make sure the directory exists
        //noinspection ResultOfMethodCallIgnored
        val exists = File(path).exists()
        if (!exists) {
            File(path).mkdirs()
        }




        var fileName: String = context.resources.getResourceEntryName(excerpt.musicResource) + ".m4a"

        var destinationFile: File = File(path, fileName)

        var mimeType: String = "audio/m4a"

        // Write the file
        var inputStream: InputStream = context.resources.openRawResource(excerpt.musicResource)
        var outputStream: FileOutputStream = FileOutputStream(destinationFile)

        try {

            // Write in 1024-byte chunks
            val buffer = ByteArray(1024)
            var bytesRead: Int
            // Keep writing until `inputStream.read()` returns -1, which means we reached the
            //  end of the stream
            while ((inputStream.read(buffer)) > 0) {
                bytesRead = inputStream.read(buffer)

                outputStream.write(buffer, 0, bytesRead)
            }

            inputStream.close()
            outputStream.flush()
            outputStream.close()

            //context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$path/$fileName")))



            // Set the file metadata


            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DATA, destinationFile.absolutePath)
            contentValues.put(MediaStore.MediaColumns.TITLE, excerpt.title)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            contentValues.put(MediaStore.Audio.Media.ARTIST, excerpt.artist)


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

            val contentUri = MediaStore.Audio.Media.getContentUriForPath(destinationFile.absolutePath)
            println("content Uri is: $contentUri")


            // If the ringtone already exists in the database, delete it first
            context.contentResolver.delete(contentUri,
                MediaStore.MediaColumns.DATA + "=\"" + destinationFile.absolutePath + "\"", null)

            // Add the metadata to the file in the database
            val newUri = context.contentResolver.insert(contentUri, contentValues)


            //var s: String = "apple"
            // Tell the media scanner about the new ringtone



            MediaScannerConnection.scanFile(
                context,
                Array(0){newUri.toString()},
                Array(0){mimeType},
                null
            )


            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+path+fileName)));

            /*
           context.sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(
               "file://" + path + fileName + Environment.getExternalStorageDirectory()
           )))
            */


            try {
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri)
            } catch (e: Exception) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(context)) {
                        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                            .setData(Uri.parse("package:" + context.packageName))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }
            }

            Toast.makeText(context, "saved file", Toast.LENGTH_LONG)
            return true;


        } catch (e: Exception) {

            Log.e("copier", "Error writing $fileName and error is: $e")

        } finally {

          //  try {
          //      inputStream?.close()
          //      outputStream?.close()
          //  } catch (e: IOException) {
          //      // Means there was an error trying to close the streams, so do nothing
          //  }

        }



        return false
*/
    }




