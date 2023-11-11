package com.cihancelik.CarParkDetails.HR.hrLocations

data class HrLocationsModel(
    var locationId: Int = 0,
    var locationName: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var addressId: Int = 0,
    var naceCode: Int = 0,
    var dangerClass: String = "",
    var updateDate: String = "",
    var creationDate: String = ""
)
