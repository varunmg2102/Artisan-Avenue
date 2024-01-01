package com.varun.artisansavenue.model

data class AllOrderModel(
    val name : String? = "",
    val orderId : String? ="",
    val image : String? ="",
    val price : String? ="",
    val status : String? ="",
    val userId : String? ="",
    val productId : String? =""
)
