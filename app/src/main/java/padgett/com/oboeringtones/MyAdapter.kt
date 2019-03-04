package padgett.com.oboeringtones

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.viewholder.view.*
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Build
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import java.util.*


class MyAdapter(val excerptList: ArrayList<OboeExcerpt>, val context: Context): RecyclerView.Adapter<MyViewHolder>() {

    var mediaPlayer: MediaPlayer? = MediaPlayer()
    var options: Array<String> = arrayOf("Ringtone", "Notification", "Alarm")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val viewForRow = layoutInflater.inflate(R.layout.viewholder, p0, false)

        return MyViewHolder(viewForRow)
    }

    override fun getItemCount(): Int {

        return excerptList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val oboeExcerpt: OboeExcerpt = excerptList[position]

        val title: String = oboeExcerpt.title
        val excerpt: Int = oboeExcerpt.musicResource

        // considered using a contextMenu but decided on alertDialog.
        // holder.itemView.setOnCreateContextMenuListener(holder)

        holder.itemView.textView.setText(title)
        var copied: Boolean
        var choice: String

        val myDialogListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                0 -> {
                    copied = copyRawSoundFile(holder.itemView.context, oboeExcerpt, MediaStore.Audio.Media.IS_RINGTONE)
                    choice = "Ringtone"}

                1 -> {
                        copied = copyRawSoundFile(holder.itemView.context, oboeExcerpt, MediaStore.Audio.Media.IS_NOTIFICATION)
                        choice = "Notification"}
                2 -> {
                        copied = copyRawSoundFile(holder.itemView.context, oboeExcerpt, MediaStore.Audio.Media.IS_ALARM)
                        choice = "Alarm"}

                else -> {
                        copied = false
                        choice = ""}
            }

            if (copied) {Toast.makeText(context, "${oboeExcerpt.title} saved as default $choice", Toast.LENGTH_SHORT).show()}
            else {Toast.makeText(context, "Failed to set as default $choice", Toast.LENGTH_SHORT).show()}
        }

        holder.itemView.setOnClickListener {

            /* // decided against using a PopupMenu.
            showMenu(holder.itemView, context)
            val popup = PopupMenu(context, holder.itemView)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener() {  })
            popup.inflate(R.menu.save_options)
            popup.show()
           */

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Save As...")
            builder.setItems(options, myDialogListener)
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        holder.itemView.imageView.setOnClickListener {

            if (mediaPlayer?.isPlaying()?: false) {
                mediaPlayer?.pause()
                holder.itemView.imageView.setImageResource(android.R.drawable.ic_media_play)

            } else {
                mediaPlayer = MediaPlayer.create(holder.itemView.context, excerpt)
                mediaPlayer?.setOnCompletionListener {
                    holder.itemView.imageView.setImageResource(android.R.drawable.ic_media_play)
                }
                mediaPlayer?.start()
                holder.itemView.imageView.setImageResource(android.R.drawable.ic_media_pause)
            }
        }


        // change color every other row color
        if (position % 2 ==0) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                holder.itemView.setBackgroundColor(context.resources.getColor(R.color.colorDarkerShade))
            } else {
                holder.itemView.setBackgroundColor(context.getColor(R.color.colorDarkerShade))
            }
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                holder.itemView.setBackgroundColor(context.resources.getColor(R.color.colorLighterShade))
            } else {
                holder.itemView.setBackgroundColor(context.getColor(R.color.colorLighterShade))
            }
        }

    }

    /*  // was considering to use PopupMenu instead of alert dialog.  Could not get a title on it.
    fun showMenu(v: View, context: Context) {
        PopupMenu(context, v).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener(this@MyAdapter)

            inflate(R.menu.save_options)
            show()
        }

    }

    // this is for the popup menu.
    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_ringtone ->
                true
            R.id.menu_notification ->
                true
            R.id.menu_alarm ->
                true
            else -> {
                println("Error with popup menu")
                Log.e("Error", "Error with the popup menu.")
                false}
        }
    }
    */

}


class MyViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    var currentItemView: View = view


/*
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        menu?.setHeaderTitle("Save As...")
        menu?.add("Ringtone")
        menu?.add("Notification")
        menu?.add("Alarm")


    }

*/


}






