package com.cihancelik.CarParkDetails.general.addressesUpdateScreen

import java.io.Serializable

data class AddressessModel(
    var addressId: Int = 0,
    var address : String = "",
    var startDate: String = "",
    var endDAte: String = "",
    var country: String = "",
    var city: String = "",
    var region: String = "",
    var postalCode: String = "",
    var addressLine: String = "",
    var updateDate: String = "",
    var creationDate: String = ""
):Serializable
