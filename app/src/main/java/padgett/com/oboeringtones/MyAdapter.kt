package padgett.com.oboeringtones

import android.graphics.Color
import android.media.MediaPlayer
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

        var title: String = excerptList.get(position).title
        var excerpt: Int = excerptList.get(position).musicResource

        holder.itemView.textView.setText(title)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Hello!", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.imageView.setOnClickListener {
            if (mediaPlayer?.isPlaying() ?: false) {
                mediaPlayer?.pause()



            }

            mediaPlayer = MediaPlayer.create(holder.itemView.context, excerpt)
            mediaPlayer?.start()

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