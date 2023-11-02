package com.cihancelik.CarParkDetails.ledger.glAccountCombinations

import java.io.Serializable

data class GLACModel (
    var glCodComId: Int = 0,
    var segment1 : String = "",
    var segment2 : String = "",
    var segment3 : String = "",
    var segment4 : String = "",
    var segment5 : String = "",
    var segmentCombination : String = "",
    var updateDate : String = "",
    var creationDate :String = ""
):Serializable