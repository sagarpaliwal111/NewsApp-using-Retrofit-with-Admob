package com.example.retrofitapicheezycode2part

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context, val article: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articles = article[position]
        holder.newsTitle.text=articles.title
        holder.newsDescription.text=articles.description
        Glide.with(context).load(articles.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener{
             Toast.makeText(context, articles.title, Toast.LENGTH_SHORT).show()
            val intent =Intent(context,DetailActivity::class.java)
            intent.putExtra("URL",articles.url)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return article.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage = itemView.findViewById<ImageView>(R.id.newsimage)
        var newsTitle = itemView.findViewById<TextView>(R.id.newstitle)
        var newsDescription = itemView.findViewById<TextView>(R.id.newsDescription)
    }
}
