package padgett.com.oboeringtones

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // my admob app id: ca-app-pub-2213157796139553~1749199127
    var context: Context

    var musicList: ArrayList<OboeExcerpt> = arrayListOf()
    var oboeExcerpt: OboeExcerpt
    var adapter: MyAdapter

    init {

        oboeExcerpt = OboeExcerpt("Bach Concerto For Oboe and Violin", R.raw.ringtone_bach_double, "Anonymous")
        populateMusicList()
        context = this
        adapter = MyAdapter(musicList, context)


    }

    lateinit var mAdView : AdView
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // what up
        // yo?

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-2213157796139553~1749199127")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mInterstitialAd = InterstitialAd(this)
        //test app interstitial id: "ca-app-pub-3940256099942544/1033173712"

        mInterstitialAd.adUnitId = "ca-app-pub-2213157796139553/1182346628"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                mInterstitialAd.show()
            }

            override fun onAdClosed() {
                Toast.makeText(this@MainActivity, "Sorry about that ad!", Toast.LENGTH_SHORT).show()
                super.onAdClosed()
            }


        }


        //registerForContextMenu(recyclerlViewMain) -- this works for ListViews and GridViews, not RecyclerViews

        //recyclerlViewMain.setBackgroundColor(Color.CYAN);

        recyclerlViewMain.layoutManager = LinearLayoutManager(this)
        recyclerlViewMain.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
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
        oboeExcerpt = OboeExcerpt("Bach, Concerto for Oboe and Violin", R.raw.ringtone_bach_double, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Bartok, Concerto for Orchestra", R.raw.ringtone_bartok, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Beethoven, 3rd Symphony", R.raw.ringtone_beethoven_3, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Mahler, 3rd Symphony", R.raw.ringtone_mahler_3, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Marcello, Concerto for Oboe D minor", R.raw.ringtone_marcello, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Ravel - Le Tombeau De Couperin, Menuet", R.raw.ringtone_ravel_menuet, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Ravel, Le Tombeau De Couperin, Forlane", R.raw.ringtone_tombeau_4th, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Strauss, Concerto for Oboe", R.raw.ringtone_strauss_concerto, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Strauss, Don Juan", R.raw.ringtone_don_juan, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Stravinsky, Pulcinella - Gavotte", R.raw.ringtone_pulcinella_gavotte, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Tchaikovsky, 4th Symphony", R.raw.ringtone_tchaikovsky_4_2nd, "Anonymous")
        musicList.add(oboeExcerpt)
        oboeExcerpt = OboeExcerpt("Vivaldi, Concerto for Oboe F Major", R.raw.ringtone_vivaldi_f, "Anonymous")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_rate_us -> {
                Toast.makeText(this, "rate us", Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=$packageName"
                    )
                    setPackage("com.android.vending")
                }
                startActivity(intent)
            }



           // R.id.action_remove_ads -> Toast.makeText(this, "remove ads", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
