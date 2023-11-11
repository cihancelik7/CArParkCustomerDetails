package com.cihancelik.CarParkDetails.HR.hrEmpAssigments

data class HrEmpAssigmentsModel(
    var assigmentId: Int = 0,
    var employeeId: Int = 0,
    var positionId: Int = 0,
    var startDate: String = "",
    var endDate: String = "",
    var updateDate: String = "",
    var creationDate: String = ""
)
