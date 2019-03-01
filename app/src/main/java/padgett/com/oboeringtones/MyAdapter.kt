package padgett.com.oboeringtones

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.viewholder.view.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import java.util.*


class MyAdapter(val excerptList: ArrayList<OboeExcerpt>, val context: Context): RecyclerView.Adapter<MyViewHolder>(), PopupMenu.OnMenuItemClickListener {

    var mediaPlayer: MediaPlayer? = MediaPlayer()
    var options: Array<String> = arrayOf("Ringtone", "Notification", "Alarm")





    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val viewForRow = layoutInflater.inflate(R.layout.viewholder, p0, false)




        return MyViewHolder(viewForRow);
    }

    override fun getItemCount(): Int {

        return excerptList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var oboeExcerpt: OboeExcerpt = excerptList.get(position)

        var title: String = oboeExcerpt.title
        var excerpt: Int = excerptList.get(position).musicResource


        holder.itemView.setOnCreateContextMenuListener(holder)

        holder.itemView.textView.setText(title)
        var copied: Boolean
        var choice: String = ""

        var myDialogListener = DialogInterface.OnClickListener { dialog, which ->
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

                else -> copied = false
            }

            if (copied) {Toast.makeText(context, "${oboeExcerpt.title} saved as default $choice", Toast.LENGTH_SHORT).show()}
            else {Toast.makeText(context, "Failed to set as default $choice", Toast.LENGTH_SHORT).show()}
        }

        holder.itemView.setOnClickListener {

            //showMenu(holder.itemView, context)
          //  val popup = PopupMenu(context, holder.itemView)
           // popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener() {  })
           // popup.inflate(R.menu.save_options)
           // popup.show()

            var builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Save As...")
            builder.setItems(options, myDialogListener)

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()



            //Toast.makeText(holder.itemView.context, "Hello!", Toast.LENGTH_SHORT).show()

            //copyRawSoundFile(holder.itemView.context, oboeExcerpt, MediaStore.Audio.Media.IS_ALARM)
        }

        holder.itemView.imageView.setOnClickListener {

            if (mediaPlayer?.isPlaying() ?: false) {
                mediaPlayer?.pause()

            } else {
                mediaPlayer = MediaPlayer.create(holder.itemView.context, excerpt)
                mediaPlayer?.start()
            }




        }

        if (position % 2 ==0) {
           // holder.itemView.setBackgroundColor(Color.RED)
        } else {
           // holder.itemView.setBackgroundColor(Color.MAGENTA)

        }



    }

    fun showMenu(v: View, context: Context) {
        PopupMenu(context, v).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener(this@MyAdapter)

            inflate(R.menu.save_options)
            show()
        }

    }

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

}


class MyViewHolder(view:View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    var currentItemView: View = view



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        menu?.setHeaderTitle("Save As...")
        menu?.add("Ringtone")
        menu?.add("Notification")
        menu?.add("Alarm")


    }




}






