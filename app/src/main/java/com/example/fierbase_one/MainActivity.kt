package com.example.fierbase_one

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.edit.*
import kotlinx.android.synthetic.main.edit.btnedit
import kotlinx.android.synthetic.main.listbook.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var rcvBook: RecyclerView
    private var db: FirebaseFirestore? = null
    private var adapter: FirestoreRecyclerAdapter<BookModel, BookViewHolder>? = null
    var count = 1
    var storg: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         db = Firebase.firestore
        reference = storg!!.reference
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("UpLoding >>>")
        progressDialog.setCancelable(false)


        fun loadimg(uri: Uri?) {
            progressDialog.show()
            reference!!.child("mah/" + UUID.randomUUID().toString()).putFile(uri!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    taskSnapshot.storage
                    Toast.makeText(this,"yes", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { exption ->


                }

        }
        btnedit.setOnClickListener {
            var Intent= Intent(this,AddBooks::class.java)
        }
    }

    private fun getAllBook() {
        rcvBook = findViewById(R.id.recycl)
        val query = db!!.collection("book")
        val options = FirestoreRecyclerOptions.Builder<BookModel>().setQuery(query,BookModel::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<BookModel, BookViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(
                    R.layout.listbook,
                    parent,
                    false
                )
                return BookViewHolder(view)
            }

            override fun onBindViewHolder(holder: BookViewHolder, position: Int, model: BookModel) {
                holder.name.text = model.bookname
                holder.athor.text = model.nameauthor
                holder.year.text = model.bookyear
                holder.price.text = model.bookprice

                holder.edit.setOnClickListener {
                    intent(
                  //   BookModel.id,
                       model.bookname,
                        model.nameauthor,
                        model.bookyear,
                        model.bookprice,
                        model.img
                         )
                }
                count++
            }


        }
        rcvBook.layoutManager = LinearLayoutManager(this)
        rcvBook.adapter = adapter
    }

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.booknamedit)!!
        var athor = view.findViewById<TextView>(R.id.authoredit)!!
        var year = view.findViewById<TextView>(R.id.yearedit)!!
        var price = view.findViewById<TextView>(R.id.priceedit)!!
        var img = view.findViewById<TextView>(R.id.imgbook)!!

        val edit = view.findViewById<Button>(R.id.btnedit)!!
    }

    fun intent(
       // id: String,
        name: String,
        athor: String,
        year: String,
        price: String,
        img: Int
    ) {
        val i = Intent(this, EditBook::class.java)
        i.putExtra("name", name)
        i.putExtra("athor", athor)
        i.putExtra("year", year)
        i.putExtra("price", price)
        i.putExtra("price", img)

        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    }
