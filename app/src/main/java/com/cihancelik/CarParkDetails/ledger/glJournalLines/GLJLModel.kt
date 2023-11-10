package com.cihancelik.CarParkDetails.ledger.glJournalLines

import android.text.Editable
import java.io.Serializable

data class GLJLModel(
    var journalLineId: Int = 0,
    var journalId: Int = 0, // FK
    var journalDate: String = "",
    var glCodComId: Int = 0,
    var accountedCrAmount: Int = 0,
    var accountedDrAmount: Int = 0,
    var updateDate: String = "",
    var creationDate: String = ""
):Serializable
