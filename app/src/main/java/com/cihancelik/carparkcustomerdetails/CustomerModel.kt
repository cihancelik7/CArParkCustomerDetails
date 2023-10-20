package com.cihancelik.carparkcustomerdetails

data class CustomerModel(
    var id: Int = 0, // Başlangıç değeri 0
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
    var address: String = "",
    var city: String = "",
    var carplate: String = ""
)
