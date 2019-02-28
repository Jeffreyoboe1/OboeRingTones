package padgett.com.oboeringtones

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var context: Context

    var musicList: ArrayList<OboeExcerpt> = arrayListOf()
    var oboeExcerpt: OboeExcerpt
    var adapter: MyAdapter

    init {

        oboeExcerpt = OboeExcerpt("Firebird Test", R.raw.firebird_test, "Anonymous")
        populateMusicList()
        context = this
        adapter = MyAdapter(musicList, context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // what up
        // yo?


        //registerForContextMenu(recyclerlViewMain) -- this works for ListViews and GridViews, not RecyclerViews

        recyclerlViewMain.setBackgroundColor(Color.CYAN);

        recyclerlViewMain.layoutManager = LinearLayoutManager(this)
        recyclerlViewMain.adapter = adapter




    }


    override fun onStop() {
            println("onStop called from mainactivity.")
            adapter.mediaPlayer?.stop()
            adapter.mediaPlayer?.release()
            adapter.mediaPlayer = null


        super.onStop()
    }

    fun populateMusicList() {
        oboeExcerpt = OboeExcerpt("Firebird Test", R.raw.firebird_test, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Oboe Test", R.raw.oboe_test, "Anonymous")
        musicList.add(oboeExcerpt)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        println("onCreateContextMenu called")
        menu?.setHeaderTitle("Save as...")
        menu?.add(0, v!!.id, 0, "Ringtone")
        menu?.add(0, v!!.id, 0, "Notification")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        Toast.makeText(baseContext, "hi", Toast.LENGTH_SHORT)
        return super.onContextItemSelected(item)
    }


}
