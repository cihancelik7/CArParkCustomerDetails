package com.cihancelik.CarParkDetails.general.userUpdateScreen

import java.io.Serializable

data class UserModel(
    var userId : Int = 0,
    var username : String = "",
    var password : String = "",
    var email : String = "",
    var startDate : String = "",
    var endDate : String = "",
    var updateDate : String = "",
    var creationDate : String = ""
) :Serializable
