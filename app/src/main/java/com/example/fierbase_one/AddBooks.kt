package com.example.fierbase_one

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.edit.*

class AddBooks: AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addbook)

         db = Firebase.firestore

        var name = findViewById<TextView>(R.id.booknamedit)
        var authorname = findViewById<TextView>(R.id.authoredit)
        var year = findViewById<TextView>(R.id.yearedit)
        var price = findViewById<TextView>(R.id.priceedit)
        val id = System.currentTimeMillis()
        btnedit.setOnClickListener {
            AddBook(
                id.toString(),
                name.text.toString(),
                authorname.text.toString(),
                year.text.toString(),
                price.text.toString(),
           //     imgbook.Int.toString(),


                )
            var Intent= Intent(this,MainActivity::class.java)

        }



    }
    private fun AddBook(id: String, name: String, athorname: String, year: String, price: String) {//,img Int

        val book = hashMapOf(
            "id" to id,
            "name" to name,
            "athor" to athorname,
            "year" to year,
            "price" to price,
          //  "img" to img
        )
        db!!.collection("book").add(book)
    }
}