package com.cihancelik.CarParkDetails.HR.hrEmployees

import java.io.Serializable

data class HrEmployeesModel(
    var employeeId: Int = 0,
    var employeeNumber: Int = 0,
    var startDate: String = "",
    var endDate: String = "",
    var isActive: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var birthDate: String = "",
    var nationalId: Long = 0,
    var martialStatus: String = "",
    var gender: String = "",
    var addressId: Int = 0,
    var emailAddress: String = ""
):Serializable
