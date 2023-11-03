package com.cihancelik.CarParkDetails.ledger.glPeriods

import java.io.Serializable

data class GLPeriodsModel(
    var periodId : Int = 0,
    var periodName: String = "",
    var periodYear : String = "",
    var updateDate : String = "",
    var creationDate : String = ""
):Serializable
