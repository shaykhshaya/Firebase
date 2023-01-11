package com.shaya.loginauthentication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaya.loginauthentication.data.Author
import com.shaya.loginauthentication.databinding.RecyclerViewAuthorItemBinding

class AuthorsAdapter : RecyclerView.Adapter<AuthorsAdapter.AuthorViewHolder>() {


    private var authors = mutableListOf<Author>()

    class AuthorViewHolder(private val binding: RecyclerViewAuthorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(author: Author) {
            binding.apply {
                textName.text = author.name
                textAge.text = author.age.toString()
                textCity.text = author.city
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val layout = RecyclerViewAuthorItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AuthorViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val currentItem = authors[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    fun setAuthors(authors: List<Author>) {
        this.authors = authors as MutableList<Author>
        notifyDataSetChanged()
    }
}