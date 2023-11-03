package com.cihancelik.CarParkDetails.ledger.glJournals

import java.io.Serializable

data class GLJournalsModel(
    var journalId : Int = 0,
    var periodId : Int = 0,
    var journalDate : String = "",
    var status : String = "",
    var amount : Int = 0,
    var updateDate : String = "",
    var creationDate : String = ""
):Serializable