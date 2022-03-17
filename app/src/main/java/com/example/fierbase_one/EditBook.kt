package com.example.fierbase_one

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.edit.*

class EditBook: AppCompatActivity()  {
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit)
         db = Firebase.firestore
        var  name = findViewById<TextView>(R.id.booknamedit)
        var athor = findViewById<TextView>(R.id.authoredit)
        var year = findViewById<TextView>(R.id.yearedit)
        var price = findViewById<TextView>(R.id.priceedit)
       // var img = findViewById<Int>(R.id.imgbook)



        name.setText(intent.getStringExtra("name").toString())
        athor.setText(intent.getStringExtra("athor").toString())
        year.setText(intent.getStringExtra("year").toString())
        price.setText(intent.getStringExtra("price").toString())
       // img.setInt(intent.getStringExtra("img").toString())


        btnedit.setOnClickListener {
            Update()
        }

        btndelet.setOnClickListener {
            DELETE()
        }
    }

    private fun Update() {
        db!!.collection("book").whereEqualTo(FieldPath.documentId(), intent.getStringExtra("id"))
            .get().addOnSuccessListener { querySnapshot ->
                db!!.collection("book").document(querySnapshot.documents.get(0).id)
                    .update(
                        "name",
                        booknamedit.text.toString(),
                        "athor",
                        authoredit.text.toString(),
                        "year",
                        yearedit.text.toString(),
                        "price",
                        priceedit.text.toString(),
                  //      "img",
                       // imgbook.Int.toString()toString
                    )
                finish()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Edit operation failed", Toast.LENGTH_LONG).show()
            }

    }

    private fun DELETE() {
        db!!.collection("book").get().addOnSuccessListener { querySnaphot ->
            for (document in querySnaphot) {
            //    document.toObject<BookModel>()
                if (document.get("id") == intent.getStringExtra("id")) {
                    db!!.collection("book").document(document.id).delete()
                }
            }
        }
    }
}

