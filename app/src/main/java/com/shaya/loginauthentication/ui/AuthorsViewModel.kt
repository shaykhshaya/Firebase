package com.shaya.loginauthentication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shaya.loginauthentication.data.Author
import com.shaya.loginauthentication.data.NODE_AUTHORS

class AuthorsViewModel : ViewModel() {

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>>
        get() = _authors

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addAuthor(author: Author) {

        author.id = dbAuthors.push().key

        dbAuthors.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }

    }

    fun fetchFilteredAuthors(index: Int) {
        val dbAuthors =
            when (index) {
                1 ->
                    //sort by name
                    FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)
                        .orderByChild("name")
                2 ->
                    //sort by age
                    FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)
                        .orderByChild("age")
                3 ->
                    //sort by city
                    FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)
                        .orderByChild("city")
                else ->
                    FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)
            }
        dbAuthors.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Author>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key
                        author?.let { authors.add(it) }
                    }
                    _authors.value = authors
                }
            }
        })
    }


    fun fetchAuthors() {
        dbAuthors.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<Author>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key
                        author?.let {
                            authors.add(it)
                        }
                    }
                    _authors.value = authors
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}