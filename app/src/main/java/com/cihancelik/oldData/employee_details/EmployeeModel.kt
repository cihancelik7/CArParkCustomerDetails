package com.cihancelik.oldData.employee_details

import java.io.Serializable

data class EmployeeModel (
    var id: Int = 0,
    var name : String = "",
    var lastName : String = "",
    var department : String = "",
    var email : String = "",
    var address : String = ""
):Serializable