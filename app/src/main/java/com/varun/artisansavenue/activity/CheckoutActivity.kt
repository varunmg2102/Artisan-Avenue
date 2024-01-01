package com.varun.artisansavenue.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.varun.artisansavenue.MainActivity
import com.varun.artisansavenue.R
import com.varun.artisansavenue.roomdb.AppDatabase
import com.varun.artisansavenue.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class CheckoutActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_TJBgAUvMWeC9N0");

        val price = intent.getStringExtra("totalCost")
        try {
            val options = JSONObject()
            options.put("name", "Artisans Avenue")
            options.put("description", "Your One Place Destination to buy Artworks")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg") //from response of step 3.
            options.put("theme.color", "#1B0B30")
            options.put("currency", "INR")
            options.put("amount", (price!!.toInt()*100)) //pass amount in currency subunits
            options.put("prefill.email", "mudyanavarun@gmail.com")
            options.put("prefill.contact", "9108241835")
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!){
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String?) {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductModel(productId))
                }

                saveData(it.getString("productName"),
                    it.getString("productSp"),
                    it.getString("productCoverImg"),
                    productId
                    )
            }

    }

    private fun saveData(name: String?, price: String?, image: String?, productId: String) {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val  data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["image"] = image!!
        data["productId"] = productId!!
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number","")!!

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key
        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show()
    }
}