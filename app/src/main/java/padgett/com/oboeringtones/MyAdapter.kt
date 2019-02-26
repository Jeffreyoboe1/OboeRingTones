package padgett.com.oboeringtones

import android.graphics.Color
import android.media.MediaPlayer
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.viewholder.view.*

class MyAdapter(val excerptList: ArrayList<OboeExcerpt>): RecyclerView.Adapter<MyViewHolder>() {

var mediaPlayer: MediaPlayer? = MediaPlayer()




    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val viewForRow = layoutInflater.inflate(R.layout.viewholder, p0, false);


        return MyViewHolder(viewForRow);
    }

    override fun getItemCount(): Int {

        return excerptList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var oboeExcerpt: OboeExcerpt = excerptList.get(position)

        var title: String = oboeExcerpt.title
        var excerpt: Int = excerptList.get(position).musicResource

        holder.itemView.textView.setText(title)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Hello!", Toast.LENGTH_SHORT).show()


            //copyRawSoundFile(holder.itemView.context, oboeExcerpt, MediaStore.Audio.Media.IS_ALARM)
        }

        holder.itemView.imageView.setOnClickListener {

            if (mediaPlayer?.isPlaying() ?: false) {
                mediaPlayer?.pause()
                Toast.makeText(holder.itemView.context, "mediaplayer is playing.  set to pause.", Toast.LENGTH_SHORT).show()

            }

            mediaPlayer = MediaPlayer.create(holder.itemView.context, excerpt)
            Toast.makeText(holder.itemView.context, "mediaplayer was not playing.  set to start.", Toast.LENGTH_SHORT).show()
            mediaPlayer?.start()
            println("mediaplayer playing: ${mediaPlayer?.isPlaying}")
            println("mediaplayer is: ${mediaPlayer.toString()}")




        }
        if (position % 2 ==0) {
            holder.itemView.setBackgroundColor(Color.RED)
        } else {
            holder.itemView.setBackgroundColor(Color.MAGENTA)

        }



    }



}


class MyViewHolder(view: View): RecyclerView.ViewHolder (view){

}