package padgett.com.oboeringtones

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var musicList: ArrayList<OboeExcerpt> = arrayListOf()
    var oboeExcerpt: OboeExcerpt
    var adapter: MyAdapter

    init {

        oboeExcerpt = OboeExcerpt("Firebird Test", R.raw.firebird_test)
        populateMusicList()
        adapter = MyAdapter(musicList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // what up
        // yo?

        recyclerlViewMain.setBackgroundColor(Color.CYAN);

        recyclerlViewMain.layoutManager = LinearLayoutManager(this)
        recyclerlViewMain.adapter = adapter


    }

    override fun onStop() {
            adapter.mediaPlayer?.stop()
            adapter.mediaPlayer?.release()
            adapter.mediaPlayer = null


        super.onStop()
    }

    fun populateMusicList() {
        oboeExcerpt = OboeExcerpt("Firebird Test", R.raw.firebird_test)
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Oboe Test", R.raw.oboe_test)
        musicList.add(oboeExcerpt)
    }


}
