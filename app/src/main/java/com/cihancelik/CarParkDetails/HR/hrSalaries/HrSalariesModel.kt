package com.cihancelik.CarParkDetails.HR.hrSalaries

import java.io.Serializable

data class HrSalariesModel(
    var salaryId: Int = 0,
    var employeeId: Int = 0,
    var amount: Int = 0,
    var startDate: String = "",
    var endDate: String = "",
    var updateDate: String = "",
    var creationDate: String = ""
):Serializable
