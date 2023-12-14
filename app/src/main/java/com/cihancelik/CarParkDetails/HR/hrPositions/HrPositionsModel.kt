package com.cihancelik.CarParkDetails.HR.hrPositions

import java.io.Serializable

data class HrPositionsModel(
    var positionId: Int = 0,
    var positionName: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var jobId : Int = 0,
    var updateDate: String = "",
    var creationDate: String = ""
):Serializable
