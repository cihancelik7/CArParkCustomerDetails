package com.cihancelik.CarParkDetails.HR.hrOrganizations

data class HrOrganizationsModel(
    var organizationId: Int = 0,
    var organizationName: String = "",
    var startDate :String = "",
    var endDate : String = "",
    var parentOrgId : Int = 0,
    var locationId : Int = 0,
    var updateDate : String = "",
    var creationDate :String = ""
)
