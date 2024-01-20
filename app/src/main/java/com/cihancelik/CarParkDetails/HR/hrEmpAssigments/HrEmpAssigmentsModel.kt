package com.cihancelik.CarParkDetails.HR.hrEmpAssigments

import java.io.Serializable

data class HrEmpAssigmentsModel(
    var assigmentId: Int = 0,
    var employeeId: Int = 0,
    var positionId: Int = 0,
    var startDate: String = "",
    var endDate: String = "",
    var updateDate: String = "",
    var creationDate: String = "",
    var empName : String = ""
):Serializable
