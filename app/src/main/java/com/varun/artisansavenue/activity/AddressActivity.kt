package com.varun.artisansavenue.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.varun.artisansavenue.R
import com.varun.artisansavenue.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!

        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userAddress.text.toString(),
                binding.pinCode.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.village.text.toString()
            )
        }
    }

    private fun validateData(number: String, name: String, address: String, pinCode: String, city: String, state: String, village: String) {
        if (number.isEmpty() || state.isEmpty() || name.isEmpty() || address.isEmpty() || pinCode.isEmpty() || city.isEmpty() || village.isEmpty()) {
            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show()
        } else {
            storeData(address, pinCode, city, state, village, number) // Include user number
        }
    }

    private fun storeData(address: String, pinCode: String, city: String, state: String, village: String, userNumber: String) {
        val map = hashMapOf<String, Any>()
        map["address"] = address
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map)
            .addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost.toString())
                val intent = Intent(this, CheckoutActivity::class.java)

                intent.putExtras(b)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get()
            .addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userAddress.setText(it.getString("address"))
                binding.village.setText(it.getString("village"))
                binding.userState.setText(it.getString("state"))
                binding.userCity.setText(it.getString("city"))
                binding.pinCode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener {
                // Handle error
            }
    }
}
