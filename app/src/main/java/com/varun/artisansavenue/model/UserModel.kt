package com.varun.artisansavenue.model

data class UserModel(
    val userName : String? = "",
    val userPhoneNumber : String? ="",
    val address : String? ="",
    val village : String? ="",
    val state : String? ="",
    val city : String? ="",
    val pinCode : String? =""
)