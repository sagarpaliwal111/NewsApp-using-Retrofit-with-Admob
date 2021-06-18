package com.example.retrofitapicheezycode2part

import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.color.MaterialColors.getColor
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var pageNumber = 1
    val TAG = "MainActivity"
    var totalResult = -1
    private var articles = mutableListOf<Article>()
    lateinit var adapter: NewsAdapter

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)
        var adRequest = AdRequest.Builder().build()



        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {



                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
//                override fun onAdClosed(interstitialAd: InterstitialAd) {
//                    super.onAdClosed()
//                    InterstitialAd.load(this@MainActivity)
//
//                }

            })



        adapter = NewsAdapter(this@MainActivity, articles)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter

        findViewById<RecyclerView>(R.id.recyclerView).layoutManager =
            LinearLayoutManager(this@MainActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {

            override fun onItemChanged(position: Int) {
                findViewById<ConstraintLayout>(R.id.container).setBackgroundColor(
                    Color.parseColor(
                        ColorPicker.getColor()
                    )
                )
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        Log.d(TAG, "Ad failed to show.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                        mInterstitialAd = null;
                    }
                }
                Log.d(TAG, "First visible item - ${layoutManager.getFirstVisibleItemPosition()}")

                Log.d(TAG, "Total item count - ${layoutManager.itemCount}")
                if (totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    pageNumber++
                    getNews()
                }
                if (position% 2 ==0) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd!!.show(this@MainActivity)
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                }


            }

        })
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = layoutManager

        getNews()
    }

    private fun getNews() {
        Log.d(TAG, "request sent for $pageNumber")
        val news = NewsService.newsInstance.getHeadlines("in", pageNumber)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    Log.d("CHEEZYCODE", news.toString())
                    totalResult = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("CHEEZYCODE", "ERROR IN FETCHING NEWS")

            }
        })
    }
}


