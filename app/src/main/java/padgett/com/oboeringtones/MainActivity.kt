package padgett.com.oboeringtones

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var musicList: ArrayList<OboeExcerpt> = arrayListOf()
    var OboeExcerpt = OboeExcerpt("Firebird Test", R.raw.firebird_test)
    var adapter: MyAdapter = MyAdapter(musicList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // what up
        // yo?

        musicList.add(OboeExcerpt)
        OboeExcerpt = OboeExcerpt("Oboe Test", R.raw.oboe_test)
        musicList.add(OboeExcerpt)





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


}
